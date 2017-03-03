package com.ihome;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ihome.node.CurrentStatus;

@RestController
public class DataController {
	@RequestMapping("/current")
	public CurrentStatus current() {
		return new CurrentStatus();
	}
}
