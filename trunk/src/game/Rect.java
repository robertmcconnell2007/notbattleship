package game;

public class Rect
{
	public int x;
	public int y;
	public int w;
	public int h;
	Rect(int nx, int ny, int nw, int nh)
	{
		x = nx;
		y = ny;
		w = nw;
		h = nh;
	}
	public boolean isIn(int ox, int oy)
	{
		if(ox > x && ox < x+w && oy > y && oy < y+h)
		{
			return true;
		}
		return false;
	}
}
