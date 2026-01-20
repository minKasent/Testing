package com.lab4.controller;

import com.lab4.dto.OrganizationDTO;
import com.lab4.entity.Organization;
import com.lab4.exception.OrganizationNameExistsException;
import com.lab4.service.OrganizationService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/organizations")
@RequiredArgsConstructor
@Slf4j
public class OrganizationController {

    private final OrganizationService organizationService;

    /**
     * Show list of organizations
     */
    @GetMapping
    public String listOrganizations(Model model) {
        model.addAttribute("organizations", organizationService.getAllOrganizations());
        return "organization/list";
    }

    /**
     * Show form to create new organization
     */
    @GetMapping("/new")
    public String showCreateForm(Model model) {
        model.addAttribute("organizationDTO", new OrganizationDTO());
        model.addAttribute("savedOrgId", null);
        return "organization/form";
    }

    /**
     * Process form submission to create organization
     */
    @PostMapping("/save")
    public String saveOrganization(
            @Valid @ModelAttribute("organizationDTO") OrganizationDTO dto,
            BindingResult bindingResult,
            Model model,
            RedirectAttributes redirectAttributes) {
        
        log.info("Saving organization: {}", dto.getOrgName());
        
        // Validation errors
        if (bindingResult.hasErrors()) {
            log.warn("Validation errors: {}", bindingResult.getAllErrors());
            model.addAttribute("savedOrgId", null);
            return "organization/form";
        }
        
        try {
            Organization saved = organizationService.createOrganization(dto);
            
            // Success - redirect to form with saved organization ID
            redirectAttributes.addFlashAttribute("successMessage", "Save successfully");
            redirectAttributes.addFlashAttribute("savedOrgId", saved.getOrgId());
            redirectAttributes.addFlashAttribute("organizationDTO", organizationService.toDTO(saved));
            
            return "redirect:/organizations/success/" + saved.getOrgId();
            
        } catch (OrganizationNameExistsException e) {
            log.warn("Organization name already exists: {}", dto.getOrgName());
            bindingResult.rejectValue("orgName", "duplicate", "Organization Name already exists");
            model.addAttribute("savedOrgId", null);
            return "organization/form";
        }
    }

    /**
     * Show success page after saving organization
     */
    @GetMapping("/success/{orgId}")
    public String showSuccessPage(@PathVariable Integer orgId, Model model) {
        Organization organization = organizationService.getOrganizationById(orgId);
        model.addAttribute("organizationDTO", organizationService.toDTO(organization));
        model.addAttribute("savedOrgId", orgId);
        model.addAttribute("successMessage", "Save successfully");
        return "organization/form";
    }
}
