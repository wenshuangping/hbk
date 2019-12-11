package com.github.common.security;

import org.springframework.security.crypto.password.PasswordEncoder;

import java.text.ParseException;

public class ZlPasswordEncoder implements PasswordEncoder {

	private final static String DES = "DES";
	public static final String HASH_ALGORITHM   = "SHA-1";
	public static final int    HASH_INTERATIONS = 1024;
	private static final int    SALT_SIZE        = 8;

	/**
     * 生成安全的密码，生成随机的16位salt并经过1024次 sha-1 hash
     */
    private String entryptPassword(final String plainPassword) {
        final byte[] salt = Digests.generateSalt(SALT_SIZE);
        final byte[] hashPassword = Digests.sha1(plainPassword.getBytes(), salt, HASH_INTERATIONS);
        return Encodes.encodeHex(salt) + Encodes.encodeHex(hashPassword);
    }

    /**
     * 验证密码
     *
     * @param plainPassword 明文密码
     * @param password 密文密码
     * @return 验证成功返回true
     */
    private boolean validatePassword(final String plainPassword, final String password) {
        final byte[] salt = Encodes.decodeHex(password.substring(0, 16));
        final byte[] hashPassword = Digests.sha1(plainPassword.getBytes(), salt, HASH_INTERATIONS);
        return password.equals(Encodes.encodeHex(salt) + Encodes.encodeHex(hashPassword));
    }
    

    public static void main(String[] args) throws ParseException {
    	ZlPasswordEncoder encoder =new ZlPasswordEncoder();
		System.out.println(encoder.entryptPassword("pig"));

		//System.out.println(
		//encoder.validatePassword("123456","8d046f8f257479b744db7bcd2a15828c0397bdf9b795ff7abb8e38d1"));



		//System.out.println("Bearer2eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJsaWNlbnNlIjoibWFkZSBieSB6bG1zZiIsI".substring(7)
		//);




	}

	@Override
	public String encode(CharSequence rawPassword) {
    	return this.entryptPassword(rawPassword.toString());
	}

	@Override
	public boolean matches(CharSequence rawPassword, String encodedPassword) {
		return this.validatePassword(rawPassword.toString(),encodedPassword);
	}


}
