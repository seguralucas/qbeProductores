package exit.services.singletons.entidadesARecuperar;

public class SFTPPropiedades {
			private String user;
			private Integer puerto;
			private String keyFile;
			private String host;
			
			public SFTPPropiedades(){}
			
			public SFTPPropiedades(String host,String user, Integer puerto, String keyFile) {
				super();
				this.host=host;
				this.user = user;
				this.puerto = puerto;
				this.keyFile = keyFile;
			}
			
			public String getUser() {
				return user;
			}
			public void setUser(String user) {
				this.user = user;
			}
			public Integer getPuerto() {
				return puerto;
			}
			public void setPuerto(Integer puerto) {
				this.puerto = puerto;
			}
			public String getKeyFile() {
				return keyFile;
			}
			public void setKeyFile(String keyFile) {
				this.keyFile = keyFile;
			}

			public String getHost() {
				return host;
			}

			public void setHost(String host) {
				this.host = host;
			}

			
}
