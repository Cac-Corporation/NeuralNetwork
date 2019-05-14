package com.cactt4ck.neuronalnetwork;

import com.cactt4ck.neuronalnetwork.Vector;

public class Affine {
	
	private final double m, p;
	
	
	public Affine(final double m, final double p) {
		this.m = m;
		this.p = p;
	}
	
	
	
	public double getSlope() {
		return this.m;
	}
	
	public double getYIntercept() {
		return this.p;
	}
	
	
	public boolean isVertical() {
		return !Double.isFinite(this.m);
	}
	
	
	public double getValue(final double x) {
		if (this.isVertical()) {
			if (x == this.p)
				return x;
			else
				return Double.NaN;
		}
			
		return (this.m * x) + this.p;
	}
	
	
	
	@Override
	public String toString() {
		String result = this.m == 0d || this.isVertical() ? "" : this.m + "x";
		
		if (p > 0d)
			result += (result.length() == 0 ? "" : " + ") + p;
		else if (p < 0d)
			result += (result.length() == 0 ? "" : " - ") + -p;
		
		return (this.isVertical() ? "x = " : "y = ") + result;
	}
	
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		long temp;
		temp = Double.doubleToLongBits(this.m);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		temp = Double.doubleToLongBits(this.p);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		return result;
	}
	
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (this.getClass() != obj.getClass())
			return false;
		
		Affine other = (Affine) obj;
		if (Double.doubleToLongBits(this.m) != Double.doubleToLongBits(other.m))
			return false;
		if (Double.doubleToLongBits(this.p) != Double.doubleToLongBits(other.p))
			return false;
		
		return true;
	}
	
	
	
	
	
	public static Vector getIntersectionPoint(final Affine first, final Affine second) {
		if (first.equals(second))
			return new Vector(Double.NaN, Double.NaN);
		else if (first.m == second.m || (first.isVertical() && second.isVertical()))
			return null;
		
		if (first.isVertical())
			return new Vector(first.p, second.getValue(first.p));
		else if (second.isVertical())
			return new Vector(second.p, first.getValue(second.p));
		
		final double x = (second.p - first.p) / (first.m - second.m);
		return new Vector(x, first.getValue(x));
	}
	
	
	public static Affine getAltitude(final Affine affine, final Vector point) {
		final double m = -1d/affine.m;
		
		return new Affine(
				m,
				point.getY() - (m * point.getX())
			);
	}
	
	
	
	
	
	public static Affine fromPoints(Vector a, Vector b) {
		if (a.getX() > b.getX()) {
			final Vector cache = b;
			b = a;
			a = cache;
		}
		
		final double m = (b.getY() - a.getY()) / (b.getX() - a.getX());
		
		final double p;
		if (!Double.isFinite(m))
			p = a.getX();
		else if (a.getX() == 0)
			p = (double)a.getY();
		else if (b.getX() == 0)
			p = (double)b.getY();
		else
			p = (double)a.getY() - (double)a.getX() * m;
		
		return new Affine(m, p);
	}
	
	
	public static Affine fromVector(final Vector origin, final Vector vector) {
		return Affine.fromPoints(origin, Vector.add(origin, vector));
	}
	
}
