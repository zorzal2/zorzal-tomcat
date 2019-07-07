package com.fontar.seguridad.cripto;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.math.MathContext;
import java.math.RoundingMode;
import java.text.DecimalFormat;

import com.fontar.seguridad.EncryptedObject;
import com.fontar.seguridad.ObjectUtils;

public class BigDecimalDecorator extends BigDecimal {
	
	private static final long serialVersionUID = 1L;

	EncryptedObject encryptedObject;
	
	public BigDecimalDecorator(EncryptedObject encryptedObject) {
		super(0);
		this.encryptedObject = encryptedObject;
	}

	private BigDecimal getBigDecimal() throws SecurityException{
		return (BigDecimal) this.encryptedObject.getObject();
	}

	public BigDecimal abs() {
		return getBigDecimal().abs();
	}


	public BigDecimal abs(MathContext mc) {
		return getBigDecimal().abs(mc);
	}


	public BigDecimal add(BigDecimal augend, MathContext mc) {
		return getBigDecimal().add(augend, mc);
	}


	public BigDecimal add(BigDecimal augend) {
		return getBigDecimal().add(augend);
	}


	public byte byteValue() {
		return getBigDecimal().byteValue();
	}


	public byte byteValueExact() {
		return getBigDecimal().byteValueExact();
	}


	public int compareTo(BigDecimal val) {
		return getBigDecimal().compareTo(val);
	}


	public BigDecimal divide(BigDecimal divisor, int scale, int roundingMode) {
		return getBigDecimal().divide(divisor, scale, roundingMode);
	}


	public BigDecimal divide(BigDecimal divisor, int scale, RoundingMode roundingMode) {
		return getBigDecimal().divide(divisor, scale, roundingMode);
	}


	public BigDecimal divide(BigDecimal divisor, int roundingMode) {
		return getBigDecimal().divide(divisor, roundingMode);
	}


	public BigDecimal divide(BigDecimal divisor, MathContext mc) {
		return getBigDecimal().divide(divisor, mc);
	}


	public BigDecimal divide(BigDecimal divisor, RoundingMode roundingMode) {
		return getBigDecimal().divide(divisor, roundingMode);
	}


	public BigDecimal divide(BigDecimal divisor) {
		return getBigDecimal().divide(divisor);
	}


	public BigDecimal[] divideAndRemainder(BigDecimal divisor, MathContext mc) {
		return getBigDecimal().divideAndRemainder(divisor, mc);
	}


	public BigDecimal[] divideAndRemainder(BigDecimal divisor) {
		return getBigDecimal().divideAndRemainder(divisor);
	}


	public BigDecimal divideToIntegralValue(BigDecimal divisor, MathContext mc) {
		return getBigDecimal().divideToIntegralValue(divisor, mc);
	}


	public BigDecimal divideToIntegralValue(BigDecimal divisor) {
		return getBigDecimal().divideToIntegralValue(divisor);
	}


	public double doubleValue() {
		return getBigDecimal().doubleValue();
	}


	public boolean equals(Object x) {
		return getBigDecimal().equals(x);
	}


	public float floatValue() {
		return getBigDecimal().floatValue();
	}


	public int hashCode() {
		return getBigDecimal().hashCode();
	}


	public int intValue() {
		return getBigDecimal().intValue();
	}


	public int intValueExact() {
		return getBigDecimal().intValueExact();
	}


	public long longValue() {
		return getBigDecimal().longValue();
	}


	public long longValueExact() {
		return getBigDecimal().longValueExact();
	}


	public BigDecimal max(BigDecimal val) {
		return getBigDecimal().max(val);
	}


	public BigDecimal min(BigDecimal val) {
		return getBigDecimal().min(val);
	}


	public BigDecimal movePointLeft(int n) {
		return getBigDecimal().movePointLeft(n);
	}


	public BigDecimal movePointRight(int n) {
		return getBigDecimal().movePointRight(n);
	}


	public BigDecimal multiply(BigDecimal multiplicand, MathContext mc) {
		return getBigDecimal().multiply(multiplicand, mc);
	}


	public BigDecimal multiply(BigDecimal multiplicand) {
		return getBigDecimal().multiply(multiplicand);
	}


	public BigDecimal negate() {
		return getBigDecimal().negate();
	}


	public BigDecimal negate(MathContext mc) {
		return getBigDecimal().negate(mc);
	}


	public BigDecimal plus() {
		return getBigDecimal().plus();
	}


	public BigDecimal plus(MathContext mc) {
		return getBigDecimal().plus(mc);
	}


	public BigDecimal pow(int n, MathContext mc) {
		return getBigDecimal().pow(n, mc);
	}


	public BigDecimal pow(int n) {
		return getBigDecimal().pow(n);
	}


	public int precision() {
		return getBigDecimal().precision();
	}


	public BigDecimal remainder(BigDecimal divisor, MathContext mc) {
		return getBigDecimal().remainder(divisor, mc);
	}


	public BigDecimal remainder(BigDecimal divisor) {
		return getBigDecimal().remainder(divisor);
	}


	public BigDecimal round(MathContext mc) {
		return getBigDecimal().round(mc);
	}


	public int scale() {
		return getBigDecimal().scale();
	}


	public BigDecimal scaleByPowerOfTen(int n) {
		return getBigDecimal().scaleByPowerOfTen(n);
	}


	public BigDecimal setScale(int newScale, int roundingMode) {
		return getBigDecimal().setScale(newScale, roundingMode);
	}


	public BigDecimal setScale(int newScale, RoundingMode roundingMode) {
		return getBigDecimal().setScale(newScale, roundingMode);
	}


	public BigDecimal setScale(int newScale) {
		return getBigDecimal().setScale(newScale);
	}


	public short shortValue() {
		return getBigDecimal().shortValue();
	}


	public short shortValueExact() {
		return getBigDecimal().shortValueExact();
	}


	public int signum() {
		return getBigDecimal().signum();
	}


	public BigDecimal stripTrailingZeros() {
		return getBigDecimal().stripTrailingZeros();
	}


	public BigDecimal subtract(BigDecimal subtrahend, MathContext mc) {
		return getBigDecimal().subtract(subtrahend, mc);
	}


	public BigDecimal subtract(BigDecimal subtrahend) {
		return getBigDecimal().subtract(subtrahend);
	}


	public BigInteger toBigInteger() {
		return getBigDecimal().toBigInteger();
	}


	public BigInteger toBigIntegerExact() {
		return getBigDecimal().toBigIntegerExact();
	}


	public String toEngineeringString() {
		return getBigDecimal().toEngineeringString();
	}


	public String toPlainString() {
		return getBigDecimal().toPlainString();
	}


	public String toString() {
		String toString = null;
		try{
			BigDecimal value = this.getBigDecimal();
			if(value!=null){
				DecimalFormat twoPlaces = new DecimalFormat("###,###.00");
				toString = twoPlaces.format( value );
			}else{
				toString = "";
			}
		}catch (Exception e) {
			toString = ObjectUtils.ENCRIPTION_WARNING;
		}
		return toString;
	}


	public BigDecimal ulp() {
		return getBigDecimal().ulp();
	}


	public BigInteger unscaledValue() {
		return getBigDecimal().unscaledValue();
	}

}
