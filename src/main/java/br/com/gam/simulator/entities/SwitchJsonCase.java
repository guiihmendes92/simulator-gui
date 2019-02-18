package br.com.gam.simulator.entities;

import br.com.gam.simulator.constants.Constants;
import com.google.gson.annotations.SerializedName;
import lombok.*;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Guilherme Mendes on 30/10/2018
 */

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class SwitchJsonCase {
	
	// PARAMETER SWITCH CASE
	public static final String CASE_NAME             = "case-name";
	public static final String CARD                  = "card";
	public static final String AUTHORIZATION         = "authorization";
	public static final String CANCELLATION          = "cancellation";
	public static final String UNDO_AUTHORIZATION    = "undo-authorization";
	public static final String UNDO_CANCELLATION     = "undo-cancellation";
	public static final String CONFIRM_AUTHORIZATION = "confirm-authorization";
	public static final String CONFIRM_CANCELLATION  = "confirm-cancellation";
	public static final String PROBE_RESPONSE        = "probe-response";
	public static final String NETWORK_MANAGEMENT    = "network-management";
	
	// PARAMETER CASE
	public static final String EXECUTION_ORDER = "execution-order";
	
	@SerializedName("connection")
	private String connection;
	
	@SerializedName(CASE_NAME)
	private String name;
	
	@SerializedName(CARD)
	private Card card;
	
	@SerializedName(AUTHORIZATION)
	private Map<String, String> authorization;
	
	@SerializedName(CANCELLATION)
	private Map<String, String> cancellation;
	
	@SerializedName(UNDO_AUTHORIZATION)
	private Map<String, String> undoAuthorization;
	
	@SerializedName(UNDO_CANCELLATION)
	private Map<String, String> undoCancellation;
	
	@SerializedName(CONFIRM_AUTHORIZATION)
	private Map<String, String> confirmAuthorization;
	
	@SerializedName(CONFIRM_CANCELLATION)
	private Map<String, String> confirmCancellation;
	
	@SerializedName(PROBE_RESPONSE)
	private Map<String, String> probeResponse;
	
	@SerializedName(NETWORK_MANAGEMENT)
	private Map<String, String> networkManagement;
	
	public Map<Constants, Map<String, String>> jsonMap() {
		
		final HashMap<Constants, Map<String, String>> jsonCases = new HashMap<>();
		
		jsonCases.put(Constants.CANCELLATION, getCancellation());
		jsonCases.put(Constants.AUTHORIZATION, getAuthorization());
		jsonCases.put(Constants.PROBE_RESPONSE, getProbeResponse());
		jsonCases.put(Constants.UNDO_CANCELLATION, getUndoCancellation());
		jsonCases.put(Constants.NETWORK_MANAGEMENT, getNetworkManagement());
		jsonCases.put(Constants.UNDO_AUTHORIZATION, getUndoAuthorization());
		jsonCases.put(Constants.CONFIRM_CANCELLATION, getConfirmCancellation());
		jsonCases.put(Constants.CONFIRM_AUTHORIZATION, getConfirmAuthorization());
		
		return jsonCases;
	}
	
}
