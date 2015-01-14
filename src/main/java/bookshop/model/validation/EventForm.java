package bookshop.model.validation;

import javax.validation.constraints.Pattern;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotEmpty;


public class EventForm {

	@NotEmpty(message = "{EventForm.StartDateDate.NotEmpty}")
	@Length(min = 10,max = 10, message = "{EventForm.StartDateDate.Length}")
	@Pattern(regexp="(0?[1-9]|[12][0-9]|3[01]).(0?[1-9]|1[012]).((19|20)\\d\\d)", message = "{Event.StartDate.Format}")
	private String dateD;
	
	@NotEmpty(message = "{EventForm.StartDateTime.NotEmpty}")
	@Length(min=5,max=5, message = "{EventForm.StartDateTime.Length}")
	@Pattern(regexp="([01]?[0-9]|2[0-3]):[0-5][0-9]")
	private String dateT;
	
	@NotEmpty(message = "{EventForm.EndDateTime.NotEmpty}")
	@Length(min=5,max=5, message = "{EventForm.EndDateTime.Length}")
	@Pattern(regexp="([01]?[0-9]|2[0-3]):[0-5][0-9]")
	private String dateTEnd;
	
	
	public String getDateD()
	{
		return dateD;
	}
	
	public void setDateD(String s)
	{
		dateD = s;
	}
	
	public String getDateT()
	{
		return dateT;
	}
	
	public void setDateT(String s)
	{
		dateT = s;
	}
	
	public String getDateTEnd()
	{
		return dateTEnd;
	}
	
	public void setDateTEnd(String s)
	{
		dateTEnd = s;
	}
}
