/**
 * 
 */
package com.demo.appliances;

import java.text.SimpleDateFormat;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.demo.entities.Appliance;
import com.demo.entities.Status;

/**
 * @author Himanshu
 *
 */
@CrossOrigin
@RestController
public class AppliancesController {
	
	static Set<Appliance> appliances = new HashSet<Appliance>();
	private static Integer idGen = 0;
	private static String getNewId() {
		idGen++;
		return idGen.toString();
	}
	static {
		
		//DateTimeFormatter formatter = DateTimeFormatter.ISO_LOCAL_DATE;
		//Date dateStr = new Date();
		String date = new SimpleDateFormat("yyyy-MM-dd",   Locale.getDefault()).format(new Date());
		
		Appliance  appliance1 = new Appliance(getNewId(),"1","LG","LGfridge",date,Status.Inuse);
		Appliance  appliance2 = new Appliance(getNewId(),"2","Daikin","Aircon1",date,Status.Inuse);
		appliances.add(appliance1);
		appliances.add(appliance2);
		
	}
	
	@GetMapping(path="/getAppliances")
	public List<Appliance> getAppliances() {
		

		List<Appliance> namesList = new ArrayList<>(appliances);
		
		return namesList;
		
	}
	
	
	@PostMapping(path="/addAppliance",headers = "Accept=application/json")
	public Appliance addAppliance(@RequestBody Appliance appliance ) {
		
		if(appliances.contains(appliance)) {
			
			return null;
		}
		appliance.setId(getNewId());
		appliances.add(appliance);
		
		
		return appliance;	
		
	}
	
	
	@DeleteMapping(path="/deleteAppliance" ,headers = "Accept=application/json")
	public ResponseEntity<Void> removeAppliance(@RequestBody Appliance appliance ) {
		
		if (appliances.contains(appliance)) {
			appliances.remove(appliance);
			return ResponseEntity.noContent().build();
		}
		return ResponseEntity.notFound().build();		
		
	}
	
	@PutMapping(path="/updateAppliance",headers = "Accept=application/json")
	public List<Appliance> updateAppliance(@RequestBody Appliance appliance ) {
		
		
		appliances.stream().forEach(x -> {
			if(x.getId().equals("")) {
				x.setBrand(appliance.getBrand());
			}
		});
			
		Set<Appliance> appSet = appliances.stream().filter(x -> !(x.getId().equals(appliance.getId()))).collect(Collectors.toSet());
		
		appSet.add(appliance);
		appliances = appSet;
		/*if(appliances.contains(appliance)) {
			
			appliances.remove(appliance);
			appliances.add(appliance);
		}
			*/	
		
		return new ArrayList<>(appliances);	
		
	}
	
	
	

}
