package com.ihome;

import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ihome.node.CurrentStatus;

@RestController
public class DataController {
//	@RequestMapping("/current")
//	public CurrentStatus current() {
//		return new CurrentStatus();
//	}

	@RequestMapping("/current")
	public CurrentStatus currentId(
			@RequestParam(value="name", required=false) String name,
			@RequestParam(value="connected", required=false) Boolean connected,
			@RequestParam(value="heatingOn", required=false) Boolean heatingOn,
			@RequestParam(value="serial", required=false) Integer serial,
			@RequestParam(value="temp", required=false) Double temp) {
		CurrentStatus c = new CurrentStatus();
		c.setName(name);
		c.setConnected(connected);
		c.setHeatingOn(heatingOn);
		c.setSerial(serial);
		c.setTemp(temp);
		return c;
	}
	
	@RequestMapping(path="/currentput", method=RequestMethod.PUT)
	public String putCurrentStatus(@RequestBody CurrentStatus cs) {
		System.out.println("GOT Status:" + cs);
		return "index";
	}
	
}
