package se.pj.tbike.core.api.product.dto;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class ProductSpecifications {

	private String size;

	private String frame;

	private String saddle;

	private String seatPost;

	private String bell;

	private String fork;

	private String shock;

	//** steering system **//

	private String handlebar;

	private String handlebarStem;

	private String pedal;

	private String crankset;

	private String bottomBracket;

	private String chain;

	private String chainGuard;

	private String cassette;

	private String frontDerailleur;

	private String rearDerailleur;

	//** motion system **//

	private String rims;

	private String hubs;

	private String spokes;

	private String tires;

	private String valve;

	private String brakes;

	private String brakeLevers;

}
