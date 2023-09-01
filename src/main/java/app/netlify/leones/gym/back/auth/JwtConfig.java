package app.netlify.leones.gym.back.auth;

public class JwtConfig {
	
	public static final String LLAVE_SECRETA = "clave.secreta.12345678 ";
	
	public static final String RSA_PRIVATE = "-----BEGIN RSA PRIVATE KEY-----\r\n"
			+ "MIIEowIBAAKCAQEAk6lzrCgEF5QrTnzq1ZX1ymBMpoc/7oFvM+3sUzjmw1Y1Envu\r\n"
			+ "+6mZfmowGikNLtrH7Udf+oXUMNrGAclTawJ8tOPfa2rswkja+ukM6IROSOfSNuwY\r\n"
			+ "vmGkpgosh/4udPtMpxI2GgBmCdo7KTRjVITxVOMOafO6DhfHeFOgeArBQOeULFFL\r\n"
			+ "46yBVUrH0kCJ/QgHMjBX1KBO4HycfmUXtQHm08d0uyjaUtJCgSbeKxT19kj9k8mg\r\n"
			+ "dFAFahpSM7SQixskLO91VehMWwFlmQmLIN3nu6IDmzwWFn1NqKbv21HXyvbyIYJL\r\n"
			+ "/1bEiarJ3WKw1UlHNOHpVrDf0drJdXxoATAF7QIDAQABAoIBAASvvBwaqQaWjUlQ\r\n"
			+ "baOZvtNNeyFN6ZmIQh9DzdxWUfyhH84WYN9b4qNAe4ZZBzIVf4LoYQNye+mRB/fG\r\n"
			+ "i/85dPvIv66JFjqUVV7hRqeai2GE+gcEN0TTlIYwjHlEd/T+hNBIo67vlE17v6Tv\r\n"
			+ "JLfqAD7RdgOm2KDZ4FrRTKaCsN9+bb3agVecb8D2bJcdS2BWMwUMb5Tq+kUcmGZo\r\n"
			+ "rH3J5gpeO4mf0+53HyqjdU+4aKY1Cv4SpOtEEriHCQPuKIAftDyWVJc/2edYl4TT\r\n"
			+ "6U9e6K7V38YcA5g5QAutyFAyKdamA3kp9tyXl+zhcrS0ESnCc2KwuSsLW30lQwGY\r\n"
			+ "ZF/9+AECgYEAxKPlOvZodVNwMzNcYmmVmtBJ0GxtcIG5rrT2qRcbd1aCeVL9hZaY\r\n"
			+ "XEhCAD4fXTtvp8A8lv7wLcCrSKJ7T5jMpucn6OBcxoDsgKdWNAAeWt6qCRgFRutG\r\n"
			+ "Uhw96llDPrQrKI131HBygJFvDklhgnxPWuWn1jahnYMA41DuqK5vSJECgYEAwDyT\r\n"
			+ "jLpW1TBa4bk+bsKDu7yNV/qXZjxwhwRgSmARSYx/VQZHM3fpc8pLRedVDw3XN+eI\r\n"
			+ "uvJMZd1m+Ha9oaZ3Tnetzb3cHpDptgAML1bgCTPK23/LRsUz/uFavt1SSTw6ya30\r\n"
			+ "zWW6Wwy9wLqOPl5wbaVeB9Sr/k7CP+IqjQ+CtZ0CgYBaaAv1LuLZxlHYiG2aJXcN\r\n"
			+ "5WN6es9aIZpwE20wUppjJJf6FcjPiWxlSSdnGyiqYbA1DPIObgmYz3Mwgtt0vgih\r\n"
			+ "V7eGntiovw99GvJMGB3co3DDm5KZh4QeLjPdMcrz4jvRejE0r7pFGBhaFizJpEOL\r\n"
			+ "w/iFSTrGwzs+R7BiR0iMsQKBgQCfQhAjKETDvZmtIyB79h3YrG6UkoBXLH80/AUh\r\n"
			+ "2NC5k/HxaJAYLaF0UgVmVi37mMXSBLkb15w/T9r8wLJ9d0Gj+jY9Gqt/jyJvqz5t\r\n"
			+ "AZX9SbEng4nOiZV39Q6OzSBcl89EQh9rAnoO8FFLl8I8HVUbIULzTddniawX7LC/\r\n"
			+ "yDfLaQKBgECWmLgsU/hg+ZTFj9uhc7rLdZm9EXIGRXyBSQ0XWm9FjDL5j4G9u3aE\r\n"
			+ "aNaT1SRgE6ArWFclsHZ9WW5ptecIGOI9zya3dJs8ouRyohqqdD4ziMIcfsHw1slm\r\n"
			+ "EoIlurystxbavjGyenSO6aCLd++T2dUx5neWCzaMnAwEBI0lQx6B\r\n"
			+ "-----END RSA PRIVATE KEY-----";
	
	public static String RSA_PUBLIC = "-----BEGIN PUBLIC KEY-----\r\n"
			+ "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAk6lzrCgEF5QrTnzq1ZX1\r\n"
			+ "ymBMpoc/7oFvM+3sUzjmw1Y1Envu+6mZfmowGikNLtrH7Udf+oXUMNrGAclTawJ8\r\n"
			+ "tOPfa2rswkja+ukM6IROSOfSNuwYvmGkpgosh/4udPtMpxI2GgBmCdo7KTRjVITx\r\n"
			+ "VOMOafO6DhfHeFOgeArBQOeULFFL46yBVUrH0kCJ/QgHMjBX1KBO4HycfmUXtQHm\r\n"
			+ "08d0uyjaUtJCgSbeKxT19kj9k8mgdFAFahpSM7SQixskLO91VehMWwFlmQmLIN3n\r\n"
			+ "u6IDmzwWFn1NqKbv21HXyvbyIYJL/1bEiarJ3WKw1UlHNOHpVrDf0drJdXxoATAF\r\n"
			+ "7QIDAQAB\r\n"
			+ "-----END PUBLIC KEY-----";

}
