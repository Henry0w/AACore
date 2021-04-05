package cold.fyre.API.Serializable;

import java.io.Serializable;

public abstract class AASerialize implements Serializable {

	private static final long serialVersionUID = 974227416574165746L;
	
	protected abstract Object deserializeObject();
	
	protected abstract AASerialize serializeObject(Object o);

}
