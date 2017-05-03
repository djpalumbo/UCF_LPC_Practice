// Solution for "shortcut"
// UCF Programming Team, fall local contest, 2010
// Jeremy Elbourn

import java.io.*;
import java.util.*;

class Point
{
	public float x, y;
	
	public Point(float X, float Y)
	{
		x = X;
		y = Y;
	}
	
	public float dist(Point p)
	{
		return (float)Math.sqrt(((p.x-x)*(p.x-x)) +((p.y-y)*(p.y-y))); 
	}
	
	public boolean eq(Point p)
	{
		return Math.abs(p.x-x) < 1e-9 && Math.abs(p.y-y) < 1e-9;
	}
}

class Line
{
	public float x0, y0, dx, dy;
	public Line (Point a, Point b)
	{
		x0 = a.x;
		y0 = a.y;
		dx = b.x - a.x;
		dy = b.y - a.y;
	}
	
	public float angle(Line f)
	{
		return (float)Math.atan2(dx*f.dy - dy*f.dx, dx*f.dx + dy*f.dy);
	}
	
	public boolean ccw(Line f)
	{
		float d = dx * f.dy - dy * f.dx;
		if (Math.abs(d) < 1e-9 || d > 0)
			return true;
		return false;
	}
}

public class shortcut 
{
	public static void main(String[] args) throws Exception
	{
		Scanner inpt = new Scanner(new File("shortcut.in"));
		Point z = new Point(0,0);
		int caseNum = 0;
		while (true)
		{
			caseNum++;
			
			// Input points defining circle
			Point c = new Point(inpt.nextFloat(), inpt.nextFloat());
			Point s = new Point(inpt.nextFloat(), inpt.nextFloat());
			Point e = new Point(inpt.nextFloat(), inpt.nextFloat());
			
			// End of data if all are zeros
			if (c.eq(z) && s.eq(z) && e.eq(z))
			{
				break;
			}
			
			// Input vertices of intra-circle segments
			int n = inpt.nextInt();
			Point[] verts = new Point[n+2];
			verts[0] = s;
			verts[n+1] = e;
			for (int i = 1; i <= n; i++)
			{
				verts[i] = new Point(inpt.nextFloat(), inpt.nextFloat());
			}
			
			// Sum distance of intra-circle segments
			float segDist = 0;
			for (int i = 0; i < verts.length - 1; i++)
			{
				segDist += verts[i].dist(verts[i+1]);
			}
			
			// Determine distance of arc
			float radius = c.dist(s);
			float circumference = (float)Math.PI * radius * 2.0f;
			Line f = new Line(c, s);
			Line g = new Line(c, e);
			float angle = f.angle(g);
			float arc = Math.abs(angle) * radius;
			
			// Because only counter-clockwise travel is permitted
			// around there circle, the opposite arc distance is
			// used if the shorter arc isn't counter-clockwise
			if (!f.ccw(g))
			{
				arc = circumference - arc;
			}
			
			//System.out.printf("angle = %f, radius = %f, circum = %f%n", angle, radius, circumference);
			//System.out.printf("arc = %f, segs = %f%n",arc, segDist);
			if (segDist < arc)
			{				
				System.out.printf("Case #%d: Watch out for squirrels!%n%n", caseNum);
			}
			else
			{
				System.out.printf("Case #%d: Stick to the Circle.%n%n", caseNum);
			}
		}
	}
}
