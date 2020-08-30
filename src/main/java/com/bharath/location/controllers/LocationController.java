package com.bharath.location.controllers;

import java.util.List;

import javax.servlet.ServletContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.bharath.location.entities.Location;
import com.bharath.location.repos.LocationRepository;
import com.bharath.location.service.LocationService;
import com.bharath.location.util.EmailUtil;
import com.bharath.location.util.ReportUtil;

@Controller
public class LocationController {
	
	
	@Autowired
	LocationService locationservice;
	
	@Autowired
	EmailUtil emailUtil;
	
	@Autowired
	LocationRepository repositery;
	@Autowired
	ReportUtil reportUtil;
	
	@Autowired
	ServletContext sc;
	
	@RequestMapping("/showCreate")
	public String showCreate() {
		
		return"createLocation";
	}

	
	
	@RequestMapping("/saveloc")
	public String saveLocation(@ModelAttribute("location") Location location, ModelMap modelMap){
		
	Location locationSave=	locationservice.saveLocation(location);
	
	String msg="Location saved with id :" +locationSave.getId();
	modelMap.addAttribute("msg",msg);
	emailUtil.sendEmail("rahulang24@gmail.com", "Location Saved ", 
			"Location Save Sucessfully and about to return a response");
	
		return"createLocation";
		
		
	}
	
	
	@RequestMapping("/displayLocations")
	public String displayLocations( ModelMap modelMap)
	{
		List<Location> locations=locationservice.getAllLocations();
		modelMap.addAttribute("locations",locations);
		return "displayLocations";
	}
	
	@RequestMapping("deleteLocations")
	public String deleteLocation(@RequestParam("id") int id, ModelMap modelMap)
	{
		Location location=locationservice.getLocationById(id);
		/*
		 * Location location=new Location(); location.setId(id);
		 * locationservice.deleteLocation(location);
		 */
		locationservice.deleteLocation(location);
		List<Location> locations=locationservice.getAllLocations();
		modelMap.addAttribute("locations",locations);
		return "displayLocations";
	}
	
	@RequestMapping("/showUpdate")
	public String showUpdate(@RequestParam("id") int id,ModelMap modelMap)
	{
		Location location= locationservice.getLocationById(id);
		//List<Location> locations=locationservice.getAllLocations();
		modelMap.addAttribute("location",location);
		return "updateLocation";
	}
	
	@RequestMapping("/updateloc")
	public String updateLocation(@ModelAttribute("location") Location location,ModelMap modelMap)
	{
		locationservice.updateLocation(location);
	
		List<Location> locations=locationservice.getAllLocations();
		modelMap.addAttribute("locations",locations);
		return "displayLocations";
	}
	
	@RequestMapping("/generateReport")
	public String generateReport()
	{
		String path = sc.getRealPath("/");
		List<Object[]> data = repositery.findTypeAndTypeCount();
		reportUtil.generatePieChart(path, data);
		
		return "report";
		
	}
	
	
	
}
