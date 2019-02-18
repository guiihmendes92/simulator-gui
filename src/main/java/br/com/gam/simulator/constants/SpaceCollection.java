package br.com.gam.simulator.constants;

import org.jpos.space.SpaceFactory;

/**
 * Created by guilherme.mendes on 29/10/2018.
 */
public enum SpaceCollection {
	
	MAIN(SpaceFactory.DEFAULT, SpaceFactory.TSPACE, false), //
	;
	
	private final String  nameSpace;
	private final String  spaceType;
	private final boolean persistentSpace;
	
	SpaceCollection(final String nameSpace, final String spaceType, final boolean persistentSpace) {
		this.nameSpace = nameSpace;
		this.spaceType = spaceType;
		this.persistentSpace = persistentSpace;
	}
	
	public String getNameSpace() {
		return nameSpace;
	}
	
	public String getSpaceType() {
		return spaceType;
	}
	
	public boolean isPersistentSpace() {
		return persistentSpace;
	}}