package br.com.gam.simulator.entities;

import com.google.gson.annotations.SerializedName;
import lombok.*;

import java.io.Serializable;

/**
 * Created by Guilherme Mendes on 30/10/2018
 */

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Card implements Serializable {
	
	// 5A
	@SerializedName("pan")
	private String pan;
	
	@SerializedName("track1")
	private String track1;
	
	@SerializedName("track2")
	private String track2;
	
	@SerializedName("password")
	private String password;
	
	@SerializedName("cvv1")
	private String cvv1;
	
	@SerializedName("cvv2")
	private String cvv2;
	
	@SerializedName("positive-identity")
	private String positiveIdentity;
	
	@SerializedName("bin")
	private String bin;
	
	@SerializedName("bin-extended")
	private String binExtended;
	
	@SerializedName("expiration-date")
	private String expirationDate;
	
	@SerializedName("card-holder")
	private String cardHolder;
	
	@SerializedName("service-code")
	private String serviceCode;
	
	@SerializedName("trailer")
	private String trailer;
	
	@SerializedName("card-scheme")
	private String cardScheme;
	
	// 5F34
	@SerializedName("psn")
	private String psn;
	
	// 84
	@SerializedName("aid")
	private String aid;
	
	// 9F10
	@SerializedName("iad")
	private String iad;
	
	// 9F10
	@SerializedName("key-pin")
	private String keyPin;
}
