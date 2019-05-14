package com.cactt4ck.neuronalnetwork;

import com.cactt4ck.neuronalnetwork.Vector;

public class Segment {
	
	public static final double APPROXIMATION_ERROR = 1d;
	
	private final Vector origin, end,
						 vector;
	private final Affine affine;
	
	
	public Segment(final Vector origin, final Vector end, final Vector vector, final Affine affine) {
		this.origin = origin;
		this.end = end;
		this.vector = vector;
		this.affine = affine;
	}
	
	
	
	public Vector getOrigin() {
		return this.origin;
	}
	
	public Vector getEnd() {
		return this.end;
	}
	
	public Vector getVector() {
		return this.vector;
	}
	
	public Affine getAffine() {
		return this.affine;
	}
	
	
	public double getLength() {
		return this.vector.getMagnitude();
	}
	
	public double getAngle() {
		return this.vector.getAngle();
	}
	
	
	
	public boolean containsPoint(final Vector point) {
		if (this.affine.isVertical())
			return point.getY() >= Math.min(this.origin.getY(), this.end.getY())
				&& point.getY() <= Math.max(this.origin.getY(), this.end.getY())
				&& Math.abs(this.affine.getYIntercept() - point.getX()) <= APPROXIMATION_ERROR;
		
		return point.getX() >= this.origin.getX()
			&& point.getX() <= this.end.getX()
			&& Math.abs(point.getY() - this.affine.getValue(point.getX())) <= APPROXIMATION_ERROR;
	}
	
	public boolean intersects(final Segment other) {
		return this.getIntersectionPoint(other) != null;
	}
	
	public double getDistanceTo(final Vector point) {
		final Segment altitude = this.getAltitude(point);
		
		if (this.getIntersectionPoint(altitude) == null)
			return -1d;
		else
			return altitude.getLength();
	}
	
	public Vector getIntersectionPoint(final Segment other) {
		final Vector intersectionPoint = Affine.getIntersectionPoint(this.affine, other.affine);
		
		return intersectionPoint != null
				&& this.containsPoint(intersectionPoint)
				&& other.containsPoint(intersectionPoint)
			? intersectionPoint
			: null;
	}
	
	public Segment getAltitude(final Vector point) {
		return Segment.fromPoints(Affine.getIntersectionPoint(this.affine, Affine.getAltitude(this.affine, point)), point);
	}
	
	

	public static Segment fromPoints(Vector origin, Vector end) {
		if (origin.getX() > end.getX()) {
			final Vector cache = end;
			end = origin;
			origin = cache;
		}
		
		return new Segment(
				origin,
				end,
				Vector.fromPoints(origin, end),
				Affine.fromPoints(origin, end)
			);
	}
	
	
	public static Segment fromVector(Vector origin, Vector vector) {
		Vector end = Vector.add(origin, vector);
		boolean invert = false;
		if(origin.getX() > end.getX()){
			Vector cache = end;
			end = origin;
			origin = cache;
			invert = true;
		}
		return new Segment(
				origin,
				end,
				invert ? vector.getOpposite() : vector,
				Affine.fromVector(origin, invert ? vector.getOpposite() : vector)
			);
	}
	
}
