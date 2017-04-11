//package com.ihome.node;
//
//import java.io.Serializable;
//
//import javax.persistence.CascadeType;
//import javax.persistence.Entity;
//import javax.persistence.GeneratedValue;
//import javax.persistence.GenerationType;
//import javax.persistence.Id;
//import javax.persistence.OneToOne;
//
//@Entity
//public class Device implements Serializable {
//
//	@Id
//	@GeneratedValue(strategy=GenerationType.AUTO)		
//	Long id;
//	String name;
//	
//	@OneToOne(cascade = CascadeType.ALL)
//	//@JoinColumn(name = )
//	private HeatingSettings settings;
//	
//	@OneToOne(cascade = CascadeType.ALL)
//	//@JoinColumn(name =)
//	private HeatingReadbackDb readback;
//	
//}
