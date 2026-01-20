package com.lab4.controller;

import com.lab4.dto.DirectorDTO;
import com.lab4.entity.Director;
import com.lab4.entity.Organization;
import com.lab4.service.DirectorService;
import com.lab4.service.OrganizationService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
@RequestMapping("/directors")
@RequiredArgsConstructor
@Slf4j
public class DirectorController {

    private final DirectorService directorService;
    private final OrganizationService organizationService;

    /**
     * Show director management form for an organization
     */
    @GetMapping("/organization/{orgId}")
    public String showDirectorForm(@PathVariable Integer orgId, Model model) {
        Organization organization = organizationService.getOrganizationById(orgId);
        List<Director> directors = directorService.getDirectorsByOrganization(orgId);
        
        DirectorDTO directorDTO = new DirectorDTO();
        directorDTO.setOrgId(orgId);
        
        model.addAttribute("organization", organization);
        model.addAttribute("directorDTO", directorDTO);
        model.addAttribute("directors", directors);
        
        return "director/form";
    }

    /**
     * Save a new director
     */
    @PostMapping("/save")
    public String saveDirector(
            @Valid @ModelAttribute("directorDTO") DirectorDTO dto,
            BindingResult bindingResult,
            Model model,
            RedirectAttributes redirectAttributes) {
        
        log.info("Saving director: {} for organization ID: {}", dto.getDirectorName(), dto.getOrgId());
        
        if (bindingResult.hasErrors()) {
            log.warn("Validation errors: {}", bindingResult.getAllErrors());
            Organization organization = organizationService.getOrganizationById(dto.getOrgId());
            List<Director> directors = directorService.getDirectorsByOrganization(dto.getOrgId());
            
            model.addAttribute("organization", organization);
            model.addAttribute("directors", directors);
            return "director/form";
        }
        
        directorService.createDirector(dto);
        redirectAttributes.addFlashAttribute("successMessage", "Director saved successfully");
        
        return "redirect:/directors/organization/" + dto.getOrgId();
    }
}
