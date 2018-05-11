package the_message_client;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Point;
import java.awt.Rectangle;
import java.io.Serializable;
import java.util.ArrayList;

@SuppressWarnings("serial")
public class Setting implements Serializable {

	public static final double VERSION = 0.0;

	public static void main(String[] args) {
	}

	// �����O�D�n�]�w�C���U�س]�w�ȥH�έp��U��JLabel��m

	// spc����t�Z�B����ϻP����϶Z�B����ϻP�ǻ��������϶Z�B����ϻP��P��Z�B��P��P�ޯ���Z
	// im������U�������ƶq���p���
	// fcc���C�����������ݵ��⪺�\��d�P�M��P�AfccSpc���L�̤������`�A���Z
	// anmSpc�ʵe�C���j(�@��)
	// shineTime�{�{����(�@��)
	// symFld���U�ޫ��s��
	public static final int chrLng = 144, nmlSpc = 10, itlWid = 71, itlHei = 100, fccWid = 100, fccHei = 140, fccSpc = 8,
			anmSpc = 6, shineTime = 400, chrImSpc = 3, stdScnWid = 9 * nmlSpc + chrLng * 6 + itlWid * 2, imLng = chrLng / 4,
			stdScnHei = 5 * nmlSpc + 4 * (chrLng + imLng), /* skFldWid = 150, */
			symFldWid = (int) (1.66f * chrLng);
	public static final float fldHeiPer = 0.8f;
	public float rate; // �P�зǹC�������j�p��������
	public int scnWidth, scnHeight; // �ڪ��C�������j�p�B��P�ϻP�ޯ�϶���
	public static final Color bg = new Color(0, 0, 0x33);

	public int seat = 0;

	public Setting(int wid, int hei) {
		double stdRate = (double) stdScnWid / stdScnHei;
		double r = (double) wid / hei;
		if (r >= stdRate)
			rate = (float) hei / stdScnHei;
		else
			rate = (float) wid / stdScnWid;
		scnWidth = (int) (stdScnWid * rate);
		scnHeight = (int) (stdScnHei * rate);
	}

	public Setting(double wid, double hei) {
		double stdRate = (double) stdScnWid / stdScnHei;
		double r = wid / hei;
		if (r >= stdRate)
			rate = (float) hei / stdScnHei;
		else
			rate = (float) wid / stdScnWid;
		scnWidth = (int) (stdScnWid * rate);
		scnHeight = (int) (stdScnHei * rate);
	}

	public ArrayList<Color> shine(Color col) {
		if (col != null) {
			ArrayList<Color> cols = new ArrayList<>();
			int r0 = col.getRed(), g0 = col.getGreen(), b0 = col.getBlue();
			for (int a = 0; a <= shineTime / anmSpc; a++) {
				float da = (float) anmSpc / shineTime;
				float alpha = da * a;
				int r = Math.round(alpha * 255 + r0 * (1 - alpha)), g = Math.round(alpha * 255 + g0 * (1 - alpha)),
						b = Math.round(alpha * 255 + b0 * (1 - alpha));
				cols.add(new Color(r, g, b));
			}
			return cols;
		}
		return null;
	}

	public int getNmlSpc() {
		return (int) (nmlSpc * rate);
	}

	public Rectangle getHcFld() {
		return new Rectangle((int) ((nmlSpc * 2 + chrLng) * rate),
				(int) ((stdScnHei - nmlSpc - getStdBigBlkHei() * fldHeiPer) * rate),
				(int) ((stdScnWid - nmlSpc * 4 - chrLng - symFldWid) * rate), (int) (getStdBigBlkHei() * fldHeiPer * rate));
	}

	public Rectangle getSymFld() {
		return new Rectangle((int) ((stdScnWid - nmlSpc - symFldWid) * rate), getChrY(seat, 9), (int) (symFldWid * rate),
				getImY(seat, 9) + getImHei() - getChrY(seat, 9));
	}

	public int getStdBigBlkHei() {
		// �����+����+��T���`����
		return chrLng + nmlSpc + imLng;
	}

	public int getChrWid() {
		return (int) (chrLng * rate);
	}

	public int getChrHei() {
		return (int) (chrLng * rate);
	}

	public int getChrLng() {
		return (int) (chrLng * rate);
	}

	public int getChrX(int $p, int total) {

		if ($p >= total)
			new SettingException("$p���p�󵥩�total�~��C$p = " + $p + ", total = " + total).printStackTrace();
		if ($p < 0 || $p > 8)
			new SettingException("$p�Ȥ����`�C$p = " + $p).printStackTrace();
		if (total < 1 || total > 9)
			new SettingException("total�Ȥ����`�Ctotal= " + total).printStackTrace();

		int p = ($p - seat + total) % total;

		switch (total) {
		case 9:
			switch (p) {
			case 0:
			case 7:
			case 8:
				return (int) (nmlSpc * rate);
			case 1:
			case 2:
				return (int) ((stdScnWid - nmlSpc - chrLng) * rate);
			case 3:
				return (int) ((stdScnWid / 2 + 1.5 * nmlSpc + chrLng) * rate);
			case 4:
				return (int) ((stdScnWid / 2 + 0.5 * nmlSpc) * rate);
			case 5:
				return (int) ((stdScnWid / 2 - 0.5 * nmlSpc - chrLng) * rate);
			case 6:
				return (int) ((stdScnWid / 2 - 1.5 * nmlSpc - 2 * chrLng) * rate);
			}
		case 8:
			switch (p) {
			case 0:
			case 6:
			case 7:
				return (int) (nmlSpc * rate);
			case 1:
			case 2:
				return (int) ((stdScnWid - nmlSpc - chrLng) * rate);
			case 3:
				return (int) ((stdScnWid / 2 + 1 * nmlSpc + 0.5 * chrLng) * rate);
			case 4:
				return (int) ((stdScnWid / 2 - 0.5 * chrLng) * rate);
			case 5:
				return (int) ((stdScnWid / 2 - nmlSpc - 1.5 * chrLng) * rate);
			}
		case 7:
			switch (p) {
			case 0:
			case 5:
			case 6:
				return (int) (nmlSpc * rate);
			case 1:
			case 2:
				return (int) ((stdScnWid - nmlSpc - chrLng) * rate);
			case 3:
				return (int) ((stdScnWid / 2 + 0.5 * nmlSpc) * rate);
			case 4:
				return (int) ((stdScnWid / 2 - 0.5 * nmlSpc - chrLng) * rate);
			}
		case 6:
			switch (p) {
			case 0:
			case 5:
				return (int) (nmlSpc * rate);
			case 1:
				return (int) ((stdScnWid - nmlSpc - chrLng) * rate);
			case 2:
				return (int) ((stdScnWid / 2 + 1 * nmlSpc + 0.5 * chrLng) * rate);
			case 3:
				return (int) ((stdScnWid / 2 - 0.5 * chrLng) * rate);
			case 4:
				return (int) ((stdScnWid / 2 - nmlSpc - 1.5 * chrLng) * rate);
			}
		case 5:
			switch (p) {
			case 0:
			case 4:
				return (int) (nmlSpc * rate);
			case 1:
				return (int) ((stdScnWid - nmlSpc - chrLng) * rate);
			case 2:
				return (int) ((stdScnWid / 2 + 0.5 * nmlSpc) * rate);
			case 3:
				return (int) ((stdScnWid / 2 - chrLng - 0.5 * nmlSpc) * rate);
			}
		case 4:
			switch (p) {
			case 0:
			case 3:
				return (int) (nmlSpc * rate);
			case 1:
				return (int) ((stdScnWid - nmlSpc - chrLng) * rate);
			case 2:
				return (int) ((stdScnWid / 2 - 0.5 * chrLng) * rate);
			}
		case 3:
			switch (p) {
			case 0:
				return (int) (nmlSpc * rate);
			case 1:
				return (int) ((stdScnWid / 2 + 0.5 * nmlSpc) * rate);
			case 2:
				return (int) ((stdScnWid / 2 - 0.5 * nmlSpc - chrLng) * rate);
			}
		case 2:
			switch (p) {
			case 0:
				return (int) (nmlSpc * rate);
			case 1:
				return (int) ((stdScnWid - chrLng) * rate / 2);
			}
		}
		return -1;
	}

	public int getChrY(int $p, int total) {

		if ($p >= total)
			new SettingException("$p���p�󵥩�total�~��C$p = " + $p + ", total = " + total).printStackTrace();
		if ($p < 0 || $p > 8)
			new SettingException("$p�Ȥ����`�C$p = " + $p).printStackTrace();
		if (total < 2 || total > 9)
			new SettingException("total�Ȥ����`�Ctotal= " + total).printStackTrace();

		int p = ($p - seat + total) % total;

		switch (total) {
		case 9:
			switch (p) {
			case 0:
				return (int) ((stdScnHei - nmlSpc - chrImSpc - (chrLng + imLng)) * rate);
			case 1:
			case 8:
				return (int) ((stdScnHei - nmlSpc * 2 - chrImSpc * 2 - (chrLng + imLng) * 2) * rate);
			case 2:
			case 7:
				return (int) ((stdScnHei - nmlSpc * 3 - chrImSpc * 3 - (chrLng + imLng) * 3) * rate);
			case 3:
			case 4:
			case 5:
			case 6:
				return (int) (nmlSpc * rate);
			}
		case 8:
			switch (p) {
			case 0:
				return (int) ((stdScnHei - nmlSpc - chrImSpc - (chrLng + imLng)) * rate);
			case 1:
			case 7:
				return (int) ((stdScnHei - nmlSpc * 2 - chrImSpc * 2 - (chrLng + imLng) * 2) * rate);
			case 2:
			case 6:
				return (int) ((stdScnHei - nmlSpc * 3 - chrImSpc * 3 - (chrLng + imLng) * 3) * rate);
			case 3:
			case 4:
			case 5:
				return (int) (nmlSpc * rate);
			}
		case 7:
			switch (p) {
			case 0:
				return (int) ((stdScnHei - nmlSpc - chrImSpc - (chrLng + imLng)) * rate);
			case 1:
			case 6:
				return (int) ((stdScnHei - nmlSpc * 2 - chrImSpc * 2 - (chrLng + imLng) * 2) * rate);
			case 2:
			case 5:
				return (int) ((stdScnHei - nmlSpc * 3 - chrImSpc * 3 - (chrLng + imLng) * 3) * rate);
			case 3:
			case 4:
				return (int) (nmlSpc * rate);
			}
		case 6:
			switch (p) {
			case 0:
				return (int) ((stdScnHei - nmlSpc - chrImSpc - (chrLng + imLng)) * rate);
			case 1:
			case 5:
				return (int) ((stdScnHei - nmlSpc * 2 - chrImSpc * 2 - (chrLng + imLng) * 2) * rate);
			case 2:
			case 3:
			case 4:
				return (int) (nmlSpc * rate);
			}
		case 5:
			switch (p) {
			case 0:
				return (int) ((stdScnHei - nmlSpc - chrImSpc - (chrLng + imLng)) * rate);
			case 1:
			case 4:
				return (int) ((stdScnHei - nmlSpc * 2 - chrImSpc * 2 - (chrLng + imLng) * 2) * rate);
			case 2:
			case 3:
				return (int) (nmlSpc * rate);
			}
		case 4:
			switch (p) {
			case 0:
				return (int) ((stdScnHei - nmlSpc - chrImSpc - (chrLng + imLng)) * rate);
			case 1:
			case 3:
				return (int) ((stdScnHei - nmlSpc * 2 - chrImSpc * 2 - (chrLng + imLng) * 2) * rate);
			case 2:
				return (int) (nmlSpc * rate);
			}
		case 3:
			switch (p) {
			case 0:
				return (int) ((stdScnHei - nmlSpc - chrImSpc - (chrLng + imLng)) * rate);
			case 1:
			case 2:
				return (int) (nmlSpc * rate);
			}
		case 2:
			switch (p) {
			case 0:
				return (int) ((stdScnHei - nmlSpc - chrImSpc - (chrLng + imLng)) * rate);
			case 1:
				return (int) (nmlSpc * rate);
			}

		}
		return -1;
	}

	public boolean isChrAtTop(int $p, int total) {
		if ($p >= total)
			new SettingException("$p���p�󵥩�total�~��C$p = " + $p + ", total = " + total).printStackTrace();
		if ($p < 0 || $p > 8)
			new SettingException("$p�Ȥ����`�C$p = " + $p).printStackTrace();
		if (total < 1 || total > 9)
			new SettingException("total�Ȥ����`�Ctotal= " + total).printStackTrace();

		int p = ($p - seat + total) % total;

		switch (total) {
		case 9:
			switch (p) {
			case 3:
			case 4:
			case 5:
			case 6:
				return true;
			}
			break;
		case 8:
			switch (p) {
			case 3:
			case 4:
			case 5:
				return true;
			}
			break;
		case 7:
			switch (p) {
			case 3:
			case 4:
				return true;
			}
			break;
		case 6:
			switch (p) {
			case 2:
			case 3:
			case 4:
				return true;
			}
			break;
		case 5:
			switch (p) {
			case 2:
			case 3:
				return true;
			}
			break;
		case 4:
			return p == 2;
		case 3:
			return p != 0;
		case 2:
			return p == 1;
		}
		return false;
	}

	// -------------------------------------------------------

	public static final int itlAnmTime = 500;// �����`�ɶ�

	public int getItlWid() {
		return (int) (itlWid * rate);
	}

	public int getItlHei() {
		return (int) (itlHei * rate);
	}

	public int getItlX(int $p, int total) {

		if ($p >= total)
			new SettingException("$p���p�󵥩�total�~��C$p = " + $p + ", total = " + total).printStackTrace();
		if ($p < 0 || $p > 8)
			new SettingException("$p�Ȥ����`�C$p = " + $p).printStackTrace();
		if (total < 1 || total > 9)
			new SettingException("total�Ȥ����`�Ctotal= " + total).printStackTrace();

		int p = ($p - seat + total) % total;

		switch (total) {
		case 9:
			switch (p) {
			case 0:
				return (int) ((stdScnWid - itlWid) * rate / 2);
			case 1:
			case 2:
				return (int) (getChrX($p, total) - getItlWid() - getNmlSpc());
			case 7:
			case 8:
				return (int) (getChrX($p, total) + getChrLng() + getNmlSpc());
			case 3:
			case 4:
			case 5:
			case 6:
				return (int) (getChrX($p, total) + (getChrLng() - getItlWid()) / 2);
			}
		case 8:
			switch (p) {
			case 0:
				return (int) ((stdScnWid - itlWid) * rate / 2);
			case 1:
			case 2:
				return (int) (getChrX($p, total) - getItlWid() - getNmlSpc());
			case 6:
			case 7:
				return (int) (getChrX($p, total) + getChrLng() + getNmlSpc());
			case 3:
			case 4:
			case 5:
				return (int) (getChrX($p, total) + (getChrLng() - getItlWid()) / 2);
			}
		case 7:
			switch (p) {
			case 0:
				return (int) ((stdScnWid - itlWid) * rate / 2);
			case 1:
			case 2:
				return (int) (getChrX($p, total) - getItlWid() - getNmlSpc());
			case 5:
			case 6:
				return (int) (getChrX($p, total) + getChrLng() + getNmlSpc());
			case 3:
			case 4:
				return (int) (getChrX($p, total) + (getChrLng() - getItlWid()) / 2);
			}
		case 6:
			switch (p) {
			case 0:
				return (int) ((stdScnWid - itlWid) * rate / 2);
			case 1:
				return (int) (getChrX($p, total) - getItlWid() - getNmlSpc());
			case 5:
				return (int) (getChrX($p, total) + getChrLng() + getNmlSpc());
			case 2:
			case 3:
			case 4:
				return (int) (getChrX($p, total) + (getChrLng() - getItlWid()) / 2);
			}
		case 5:
			switch (p) {
			case 0:
				return (int) ((stdScnWid - itlWid) * rate / 2);
			case 1:
				return (int) (getChrX($p, total) - getItlWid() - getNmlSpc());
			case 4:
				return (int) (getChrX($p, total) + getChrLng() + getNmlSpc());
			case 2:
			case 3:
				return (int) (getChrX($p, total) + (getChrLng() - getItlWid()) / 2);
			}
		case 4:
			switch (p) {
			case 0:
				return (int) ((stdScnWid - itlWid) * rate / 2);
			case 1:
				return (int) (getChrX($p, total) - getItlWid() - getNmlSpc());
			case 3:
				return (int) (getChrX($p, total) + getChrLng() + getNmlSpc());
			case 2:
				return (int) (getChrX($p, total) + (getChrLng() - getItlWid()) / 2);
			}
		case 3:
			switch (p) {
			case 0:
				return (int) ((stdScnWid - itlWid) * rate / 2);
			case 1:
			case 2:
				return (int) (getChrX($p, total) + (getChrLng() - getItlWid()) / 2);
			}
		case 2:
			switch (p) {
			case 0:
				return (int) ((stdScnWid - itlWid) * rate / 2);
			case 1:
				return (int) (getChrX($p, total) + (getChrLng() - getItlWid()) / 2);
			}
		}
		return -1;
	}

	public int getItlY(int $p, int total) {

		if ($p >= total)
			new SettingException("$p���p�󵥩�total�~��C$p = " + $p + ", total = " + total).printStackTrace();
		if ($p < 0 || $p > 8)
			new SettingException("$p�Ȥ����`�C$p = " + $p).printStackTrace();
		if (total < 1 || total > 9)
			new SettingException("total�Ȥ����`�Ctotal= " + total).printStackTrace();

		int p = ($p - seat + total) % total;

		switch (total) {
		case 9:
			switch (p) {
			case 0: // TODO
				return getTimelineY() - getNmlSpc() - getItlHei();
			case 1:
			case 2:
			case 7:
			case 8:
				return (int) (getChrY($p, total) + getChrLng() - getItlHei());
			case 3:
			case 4:
			case 5:
			case 6:
				return (int) (getImY($p, total) + getImHei() + getNmlSpc());
			}
		case 8:
			switch (p) {
			case 0:
				return getTimelineY() - getNmlSpc() - getItlHei();
			case 1:
			case 2:
			case 6:
			case 7:
				return (int) (getChrY($p, total) + getChrLng() - getItlHei());
			case 3:
			case 4:
			case 5:
				return (int) (getImY($p, total) + getImHei() + getNmlSpc());
			}
		case 7:
			switch (p) {
			case 0:
				return getTimelineY() - getNmlSpc() - getItlHei();
			case 1:
			case 2:
			case 5:
			case 6:
				return (int) (getChrY($p, total) + getChrLng() - getItlHei());
			case 3:
			case 4:
				return (int) (getImY($p, total) + getImHei() + getNmlSpc());
			}
		case 6:
			switch (p) {
			case 0:
				return getTimelineY() - getNmlSpc() - getItlHei();
			case 1:
			case 5:
				return (int) (getChrY($p, total) + getChrLng() - getItlHei());
			case 2:
			case 3:
			case 4:
				return (int) (getImY($p, total) + getImHei() + getNmlSpc());
			}
		case 5:
			switch (p) {
			case 0:
				return getTimelineY() - getNmlSpc() - getItlHei();
			case 1:
			case 4:
				return (int) (getChrY($p, total) + getChrLng() - getItlHei());
			case 2:
			case 3:
				return (int) (getImY($p, total) + getImHei() + getNmlSpc());
			}
		case 4:
			switch (p) {
			case 0:
				return getTimelineY() - getNmlSpc() - getItlHei();
			case 1:
			case 3:
				return (int) (getChrY($p, total) + getChrLng() - getItlHei());
			case 2:
				return (int) (getImY($p, total) + getImHei() + getNmlSpc());
			}
		case 3:
			switch (p) {
			case 0:
				return getTimelineY() - getNmlSpc() - getItlHei();
			case 1:
			case 2:
				return (int) (getImY($p, total) + getImHei() + getNmlSpc());
			}
		case 2:
			switch (p) {
			case 0:
				return getTimelineY() - getNmlSpc() - getItlHei();
			case 1:
				return (int) (getImY($p, total) + getImHei() + getNmlSpc());
			}
		}
		return -1;
	}

	// ---------------------------------------------------------

	public static final Color imRed = Color.RED;
	public static final Color imBlue = new Color(0, 0x99, 0xff);
	public static final Color imBlack = Color.BLACK;
	public static final Color imHand = new Color(0, 0x66, 0);

	public int getImX(int p, int total, String color) {

		if (p >= total)
			new SettingException("p���p�󵥩�total�~��Cp = " + p + ", total = " + total).printStackTrace();
		if (p < 0 || p > 8)
			new SettingException("p�Ȥ����`�Cp = " + p).printStackTrace();
		if (total < 1 || total > 9)
			new SettingException("total�Ȥ����`�Ctotal= " + total).printStackTrace();

		int x = getChrX(p, total);
		if (x == -1) // ��J���~�����G
			return -1;

		switch (color) {
		case "��P":
		case "h":
		case "H":
			return x;
		case "��":
		case "r":
		case "R":
			return (int) (x + imLng * rate);
		case "��":
		case "B":
		case "b":
			return (int) (x + imLng * 2 * rate);
		case "��":
		case "��":
		case "k":
		case "K":
			return (int) (x + imLng * 3 * rate);
		default:
			new SettingException("color���ȿ��~�Ccolor = " + color);
			return -1;
		}
	}

	public int getImY(int p, int total) {

		if (p >= total)
			new SettingException("p���p�󵥩�total�~��Cp = " + p + ", total = " + total).printStackTrace();
		if (p < 0 || p > 8)
			new SettingException("p�Ȥ����`�Cp = " + p).printStackTrace();
		if (total < 1 || total > 9)
			new SettingException("total�Ȥ����`�Ctotal= " + total).printStackTrace();

		int x = getChrY(p, total);
		if (x == -1)
			return -1;
		return (int) (x + (chrImSpc + chrLng) * rate);
	}

	public int getImLng() {
		return (int) (imLng * rate);
	}

	public int getImHei() {
		return (int) (imLng * rate);
	}

	public int getImWid(int p, int total, String color) {

		if (p >= total)
			new SettingException("p���p�󵥩�total�~��Cp = " + p + ", total = " + total).printStackTrace();
		if (p < 0 || p > 8)
			new SettingException("p�Ȥ����`�Cp = " + p).printStackTrace();
		if (total < 1 || total > 9)
			new SettingException("total�Ȥ����`�Ctotal= " + total).printStackTrace();

		switch (color) {
		case "��P":
		case "h":
		case "H":
			return getImX(p, total, "r") - getImX(p, total, "h");
		case "��":
		case "r":
		case "R":
			return getImX(p, total, "b") - getImX(p, total, "r");
		case "��":
		case "b":
		case "B":
			return getImX(p, total, "k") - getImX(p, total, "b");
		case "��":
		case "��":
		case "K":
		case "k":
			return getChrX(p, total) + getChrWid() - getImX(p, total, "k");
		default:
			new SettingException("color���ȿ��~�Ccolor = " + color);
			return (int) (imLng * rate);
		}
	}

	public Font getImFont() {
		return new Font("�L�n������", Font.PLAIN, (int) (0.8 * getImHei()));
	}

	public int getHandcardX(int n, int total, int focus) {

		if (n >= total)
			new SettingException("n���p�󵥩�total�~��Cn = " + n + ", total = " + total).printStackTrace();
		if (n < 0)
			new SettingException("n�Ȥ��i�p��0�Cn = " + n).printStackTrace();
		if (total < 1)
			new SettingException("total�ݬ����ơCtotal= " + total).printStackTrace();
		if ((focus != -1 && focus < 0) || focus >= total)
			new SettingException("focus�Ȥ����`�Cfocus= " + focus).printStackTrace();

		// isFocus��ܬO�_���Q����A-1��L�A�_�h��Q�諸�Ƨ�
		// n=0;
		if (total * getFccWid() + (total - 1) * getFccSpc() > getHcFld().width) {
			// �@�붡�Z�뤣�U
			if (total * getFccWid() > getHcFld().width) {
				// �|�B��
				// xx�O�Q�B�\���P�S�X�Ӫ������e��
				float xx = ((float) (getHcFld().width - 2 * getFccWid() - getFccSpc()) / (total - 2));
				if (focus == -1)
					return Math.round(getHcFld().x + n * xx);
				// ���U�ӬO��focus��������
				else if (n <= focus)
					return Math.round(getHcFld().x + n * xx);
				else
					return Math.round(getHcFld().x + (n - 1) * xx + getFccWid() + getFccSpc());
			} else {
				// �@��뤣�U���S���|�B��
				// ss��s���Z
				float ss = (float) (getHcFld().width - total * getFccWid()) / (total - 1);
				return Math.round(getHcFld().x + n * (getFccWid() + ss));
			}
		} else {
			// �P�ܤ֡AOK
			// m����t�Z
			int m = (getHcFld().width - total * getFccWid() - (total - 1) * getFccSpc()) / 2;
			return getHcFld().x + n * (getFccWid() + getFccSpc()) + m;
		}
	}

	public int getHandcardY(boolean clickable) {
		if (!clickable) // ���i��
			return getHcFld().y + (getHcFld().height - getFccHei()) / 2;
		else
			return getHandcardY(false) - (int) (hcUp * rate);
	}

	public int getHandcardY() {
		return getHandcardY(false);
	}

	public static final int hcUp = 20;

	// -----------------------

	public static final int choChrLng = 180, choChrFldMgn = 40, choChrFldSpc = 20, choChrBtnHei = choChrLng / 4; // �﨤�⪺����Ϥj�p�Bmargin�B�Ϲ϶���B���s��(�e�P�ϦP)
	private static final int choChrFldWid = choChrFldMgn * 2 + choChrFldSpc + choChrLng * 2,
			choChrFldHei = choChrFldMgn * 2 + choChrFldSpc + choChrLng + choChrBtnHei;
	private static final Rectangle choChrFld = new Rectangle((stdScnWid - choChrFldWid) / 2, (stdScnHei - choChrFldHei) / 2,
			choChrFldWid, choChrFldHei);

	public Font getChoChrFont() {
		return new Font("�L�n������", Font.PLAIN, (int) (choChrBtnHei * 0.65 * rate));
	}

	public Rectangle getChoChrFld() {
		return new Rectangle((int) (choChrFld.x * rate), (int) (choChrFld.y * rate), (int) (choChrFld.width * rate),
				(int) (choChrFld.height * rate));
	}

	public Rectangle getChoChrPic(int num) {
		return new Rectangle((int) (choChrFldMgn + (choChrFldSpc + choChrLng) * (num - 1) * rate),
				(int) (choChrFldMgn * rate), (int) (choChrLng * rate), (int) (choChrLng * rate));
	}

	public Rectangle getChoChrBtn(int num) {
		return new Rectangle((int) (choChrFldMgn + (choChrFldSpc + choChrLng) * (num - 1) * rate),
				(int) ((choChrFldMgn + choChrLng + choChrFldSpc) * rate), (int) (choChrLng * rate),
				(int) (choChrBtnHei * rate));
	}

	public static final Color onExited_col = new Color(0, 168, 223), onEntered_col = new Color(51, 204, 255);

	// ------------------------

	public int getFccWid() {
		return (int) (fccWid * rate);
	}

	public int getFccHei() {
		return (int) (fccHei * rate);
	}

	public int getFccSpc() {
		return (int) (fccSpc * rate);
	}

	// ------------------------

	// �o�O?

	public int getFreeFldX() {
		return (int) (scnWidth * 0.1);
	}

	public int getFreeFldWid() {
		return (int) (scnWidth * 0.8);
	}

	public int getFreeFldY() {
		return (int) ((nmlSpc * 3 + chrLng + imLng) * rate);
	}

	public int getFreeFldHei() {
		return (int) ((stdScnHei - nmlSpc * 6 - chrLng * 2 - imLng * 2) * rate);
	}

	// --------------------------

	public static final int anmDrawCardTime = 400, anmDrawCardShowTime = 500;
	// ��P�ʵe����(�@��)�A�d�P�X�{��Ȱ����ɶ�

	public int getAnmDrawCardX(int n, int t) {
		// ����k�i�H�o���P�ʵe�ɵP�ϰ_�lx��
		// n�ĴX�i�P�A�Ĥ@�i��0
		// t�@��X�i�P

		if (n >= t)
			new SettingException("n�ݤp��t�Cn = " + n + " t = " + t).printStackTrace();
		if (n < 0)
			new SettingException("n���i���t�ơCn = " + n).printStackTrace();
		if (t < 1)
			new SettingException("t�ݬ����ơCt =" + t).printStackTrace();

		int W = getFccSpc() * (t - 1) + getFccWid() * t; // �`�e
		if (W <= getFreeFldWid()) {
			// �Ŷ��D�`����
			int m = (getFreeFldWid() - W) / 2; // ��t�Z��
			return getFreeFldX() + m + (getFccSpc() + getFccWid()) * n;
		} else if (getFccWid() * t <= getFreeFldWid()) {
			// �i�H���o�U
			float ss = (float) (getFreeFldWid() - getFccWid() * t) / (t - 1); // s�s���Z
			return getFreeFldX() + Math.round((ss + getFccWid()) * n);
		} else {
			// ���|��F
			float xx = (float) (getFreeFldWid() - getFccWid()) / (t - 1); // �Q�B�d�P�S�X�Ӫ��e��
			return getFreeFldX() + Math.round(xx * n);
		}
	}

	public int getAnmDrawCardY() {
		return getFreeFldY() + (getFreeFldHei() - getFccHei()) / 2;
	}

	// ---------------------------

	public static final int choChr_time = 60000; // �﨤�⭭��ɶ�

	public int getTimelineHei() {
		return (int) (4 * rate);
	}

	public int getTimelineWid() {
		return getHcFld().width - getGamechatBtnWid();
	}

	public int getTimelineX() {
		return getHcFld().x;
	}

	public int getTimelineY() {
		return getHcFld().y - getTimelineHei();
	}

	public Rectangle getTimeline() {
		return new Rectangle(getHcFld().x, getSymFld().y, getHcFld().width, getHcFld().y - getSymFld().y);
	}

	// ---------------------------

	public static final int anmUseCardTime = 80; // �X�P�ʵe�ɶ�(�@��)

	public int getFccX(int n, int t, int isFocus) {
		if (t * getFccWid() + (t - 1) * getFccSpc() <= getFreeFldWid()) {
			// �Ŷ��D�`����
			int mgn = (getFreeFldWid() - t * getFccWid() - (t - 1) * getFccSpc()) / 2;
			return getFreeFldX() + mgn + n * (getFccWid() + getFccSpc());
		} else if (t * getFccWid() + (t - 1) <= getFreeFldWid()) {
			// �Ŷ��ٶ�o�U
			float ss = (float) (getFreeFldWid() - t * getFccWid()) / (t - 1); // �s���Z
			return Math.round(getFreeFldX() + n * (getFccWid() + ss));
		} else {
			float xx = (float) (getFreeFldWid() - 3 * getFccWid() - 2 * getFccSpc()) / (t - 3); // ���|����
			if (isFocus == -1 || n <= isFocus)
				return (int) (getFreeFldX() + xx * n);
			else
				return (int) (getFreeFldX() + xx * (n - 1) + getFccWid() + getFccSpc());
		}
	}

	public int getFccX(int n, int t, int focus1, int focus2) {

		// ����k�Φb�ѯ}���A�ƹ��@�D�d�P�W�ɨϥΡA���ɦ����focus

		if (focus1 > focus2) { // focus1�ݤp�󵥩�focus2
			int tmp = focus1;
			focus1 = focus2;
			focus2 = tmp;
		}

		if (t * getFccWid() + (t - 1) * getFccSpc() <= getFreeFldWid()) {
			// �Ŷ��D�`����
			int mgn = (getFreeFldWid() - t * getFccWid() - (t - 1) * getFccSpc()) / 2;
			return getFreeFldX() + mgn + n * (getFccWid() + getFccSpc());
		} else if (t * getFccWid() + (t - 1) <= getFreeFldWid()) {
			// �Ŷ��ٶ�o�U
			float ss = (float) (getFreeFldWid() - t * getFccWid()) / (t - 1); // �s���Z
			return Math.round(getFreeFldX() + n * (getFccWid() + ss));
		} else {
			float xx = (float) (getFreeFldWid() - 3 * getFccWid() - 2 * getFccSpc()) / (t - 3); // ���|����
			if (n <= focus1) // �諸�P�b�ܥ���
				// �뤣�U�|���|
				return (int) (getFreeFldX() + n * xx);
			else if (n > focus2) // �諸�P�b�ܥk��
				return Math.round(getFreeFldX() + xx * (n - 2) + 2 * getFccSpc() + 2 * getFccWid());
			else // �����q
				return Math.round(getFreeFldX() + xx * (n - 1) + getFccWid() + getFccSpc());
		}
	}

	public int getFccY() {
		return getFreeFldY() + (getFreeFldHei() - getFccHei()) / 2;
	}

	public Rectangle getIniFunccard(int p, int t) {
		// p�ֵo������
		// t���W�`�H��
		// �ϥΥd�P�ʵe�ɥd�P����l��m
		return new Rectangle(getChrX(p, t) + (getChrWid() - getFccWid()) / 2,
				getChrY(p, t) + (getChrHei() - getFccHei()) / 2, getFccWid(), getFccHei());
	}

	// ---------------------------

	public Rectangle getSkill(int n, int t) {
		int x = getSymFld().x + getBtnWid() + getNmlSpc();
		int w = getSymFld().width - getBtnWid() - getNmlSpc();
		int y0 = Math.round(getSymFld().y + getBtnHeif() + getBtnSpc());
		int H = getSymFld().y + getSymFld().height - y0;
		float h = (float) ((H - (t - 1) * getNmlSpc())) / t;
		return new Rectangle(x, y0 + Math.round((h + getNmlSpc()) * n), w, Math.round(h));
	}

	// ---------------------------

	public Point getBtnLoc(int which) {
		// which �ĴX�ӫ��s �̤W�謰0�A�̦h��3
		if (which >= 0 && which < group)
			return new Point(getSymFld().x, getSymFld().y + Math.round(getBtnHeif() * which));
		else if (which < btnCount)
			return new Point(getSymFld().x, getSymFld().y + getBtnSpc() + Math.round(getBtnHeif() * which));
		new SettingException("which ������ 0 �� " + btnCount + " ����: which = " + which).printStackTrace();
		return null;

	}

	public static final int btnCount = 5;
	public static final int group = 3;

	public int getBtnHei() {
		return Math.round(getBtnHeif());
	}

	public float getBtnHeif() {
		return (float) (getSymFld().height - getBtnSpc()) / btnCount;
	}

	public int getBtnWid() {
		return Math.round(getBtnHeif() * 2);
	}

	public Dimension getBtnSize() {
		return new Dimension(getBtnWid(), getBtnHei());
	}

	public static final int btnSpc = 3;

	public int getBtnSpc() {

		return (int) (btnSpc * rate);

	}

	// ----------------------------

	public static final int anmUpTime = 667; // �B�_�өһݮɶ�
	public static final int anmUpAndDownPauseTime = 333; // �����Ȱ��ɶ�
	public static final int anmDownTime = 333; // �U�h�һݮɶ�

	// ------------------------------------

	public static final int anmltdTime = 100; // ��w�ժ�I��ܷN�ʵe�ɶ�

	// -----------------------------------------------

	public static final int itlFldTtHei = 36;

	public Rectangle getItlFld() {
		// int i = getScrWid();
		return new Rectangle(getItlFldX(), getItlFldY(), getItlFldWid(), getItlFldHei());
	}

	public int getItlFldHei() {
		return getFccHei() + getNmlSpc() * 2 + getItlFldTtHei();
	}

	public int getItlFldTtHei() {
		return (int) (itlFldTtHei * rate);
	}

	public int getItlFldWid() {
		return scnWidth - getNmlSpc() * 4 - getChrLng() * 2;
	}

	public int getItlFldY() {
		return (scnHeight - getItlFldHei()) / 2;
	}

	public int getItlFldX() {
		return getNmlSpc() * 2 + getChrLng();
	}

	public int getImItlY() {
		return getItlFldTtHei() + getNmlSpc();
	}

	public int getImItlX(int index, int total, int focus) {
		// focus��-1
		if (index < 0) {
			new LogicException("index���i���t��: index = " + index).printStackTrace();
			return -1;
		} else if (total <= 0) {
			new LogicException("total�ݤj��0: total = " + total).printStackTrace();
			return -1;
		} else if (index >= total) {
			new LogicException("index�ݤp��total: index =  " + index + ", total = " + total).printStackTrace();
			return -1;
		} else if (focus >= total) {
			new LogicException("focus�ݤp��total: focus =  " + focus + ", total = " + total).printStackTrace();
			return -1;
		}

		if (total * getFccWid() + (total - 1) * getNmlSpc() <= getItlFldWid()) {
			int w = total * getFccWid() + (total - 1) * getNmlSpc();
			int x0 = (getItlFldWid() - w) / 2;
			return x0 + (getFccWid() + getNmlSpc()) * index;
		} else if (total * getFccWid() <= getItlFldWid()) {
			float s = (float) (getItlFldWid() - total * getFccWid()) / (total - 1);
			return Math.round((getFccWid() + s) * index);
		} else {
			float x = (float) (getItlFldWid() - getNmlSpc() - 2 * getFccWid()) / (total - 2);
			if (focus == -1 || index < focus)
				return Math.round(x * index);
			else
				return Math.round(x * (index - 1) + getFccWid() + getNmlSpc());
		}

	}

	public Rectangle getSeeItlFldCloseBtn() {
		return new Rectangle(getItlFldWid() - getItlFldTtHei(), 0, getItlFldTtHei(), getItlFldTtHei());
	}

	// -------------------------------------------------------------

	public int getLotSpc() {
		return getNmlSpc();
	}

	public Point getLotLoc(int n, int total) {

		if (n >= total || n < 0) {
			new LogicException("total�ݤ���2~9: " + total).printStackTrace();
			return null;
		}

		if (total > 1 && total < 10) {
			float xx = (float) getFccWid() * 2 / 3, yy = (float) getFccHei() * 2 / 3, ii = (float) getFccWid() * 1 / 3;
			int x = -1, y = -1;
			switch (total) {
			case 2:
			case 3:
			case 4:
				x = Math.round(getLotSpc() + xx * n);
				y = getLotSpc();
				break;
			case 5:
				switch (n) {
				case 0:
				case 1:
				case 2:
					x = Math.round(getLotSpc() + xx * n);
					y = getLotSpc();
					break;
				case 3:
				case 4:
					x = Math.round(getLotSpc() + xx / 2 + xx * (n - 3));
					y = Math.round(getLotSpc() + yy);
					break;
				}
				break;
			case 6:
				switch (n) {
				case 0:
				case 1:
				case 2:
					x = Math.round(getLotSpc() + xx * n);
					y = getLotSpc();
					break;
				case 3:
				case 4:
				case 5:
					x = Math.round(getLotSpc() + xx * (n - 3) + ii);
					y = Math.round(getLotSpc() + yy);
					break;
				}
				break;
			case 7:
				switch (n) {
				case 0:
				case 1:
				case 2:
				case 3:
					x = Math.round(getLotSpc() + xx * n);
					y = getLotSpc();
					break;
				case 4:
				case 5:
				case 6:
					x = Math.round(getLotSpc() + xx / 2 + xx * (n - 4));
					y = Math.round(getLotSpc() + yy);
					break;
				}
				break;
			case 8:
				switch (n) {
				case 0:
				case 1:
				case 2:
				case 3:
					x = Math.round(getLotSpc() + xx * n);
					y = getLotSpc();
					break;
				case 4:
				case 5:
				case 6:
				case 7:
					x = Math.round(getLotSpc() + xx * (n - 4) + ii);
					y = Math.round(getLotSpc() + yy);
					break;
				}
				break;
			case 9:
				switch (n) {
				case 0:
				case 1:
				case 2:
					x = Math.round(getLotSpc() + xx * (n - 4));
					y = getLotSpc();
					break;
				case 3:
				case 4:
				case 5:
					x = Math.round(getLotSpc() + xx * (n - 3) + ii);
					y = Math.round(getLotSpc() + yy);
					break;
				case 6:
				case 7:
				case 8:
					x = Math.round(getLotSpc() + xx * (n - 6) + ii * 2);
					y = Math.round(getLotSpc() + yy * 2);
					break;
				}
				break;
			}
			return new Point(x + getLotFld(total).x, y + getLotFld(total).y);
		} else {
			new LogicException("total�ݤ���2~9: " + total).printStackTrace();
			return null;
		}
	}

	public Rectangle getLotFld(int total) {

		if (total > 1 && total < 10) {

			float xx = (float) getFccWid() * 2 / 3, yy = (float) getFccHei() * 2 / 3, ii = (float) getFccWid() * 1 / 3;

			int x = -1, y = -1, w = -1, h = -1;
			switch (total) {
			case 2:
			case 3:
			case 4:
				w = Math.round(getLotSpc() * 2 + xx * (total - 1) + getFccWid());
				h = getLotSpc() * 2 + getFccHei();
				break;
			case 5:
				w = Math.round(getLotSpc() * 2 + xx * 2 + getFccWid());
				h = Math.round(getLotSpc() * 2 + getFccHei() + yy);
				break;
			case 6:
				w = Math.round(getLotSpc() * 2 + xx * 2 + getFccWid() + ii);
				h = Math.round(getLotSpc() * 2 + getFccHei() + yy);
				break;
			case 7:
				w = Math.round(getLotSpc() * 2 + xx * 3 + getFccWid());
				h = Math.round(getLotSpc() * 2 + getFccHei() + yy);
				break;
			case 8:
				w = Math.round(getLotSpc() * 2 + xx * 3 + getFccWid() + ii);
				h = Math.round(getLotSpc() * 2 + getFccHei() + yy);
				break;
			case 9:
				w = Math.round(getLotSpc() * 2 + xx * 2 + getFccWid() + ii * 2);
				h = Math.round(getLotSpc() * 2 + getFccHei() + yy * 2);
				break;
			}

			x = (scnWidth - w) / 2;
			y = (scnHeight - h) / 2;
			return new Rectangle(x, y, w, h);

		} else {
			new LogicException("total�ݤ���2~9: " + total).printStackTrace();
			return null;
		}
	}

	// ---------------------------------------------------------------

	public int getSeeItlFldSpc() {
		return (int) (getFccWid() * 0.4);
	}

	public int getSeeItlFldSpc2() {
		return (int) (getFccWid() * 0.3);
	}

	public int getSeeItlTxtWid() {
		return (int) (getFccWid() * 1.8);
	}

	public Rectangle getSeeItlFld() {
		return new Rectangle((scnWidth - getSeeItlFldWid()) / 2, (scnHeight - getSeeItlFldHei()) / 2, getSeeItlFldWid(),
				getSeeItlFldHei());
	}

	public int getSeeItlFldWid() {
		return getSeeItlFldSpc() * 3 + getFccWid() + getSeeItlTxtWid();
	}

	public int getSeeItlFldHei() {
		return getFccHei() + getSeeItlFldSpc() * 2;
	}

	public static final float seeItlbtn = 0.2f;

	public int getSeeItlBtnHei() {
		return Math.round(seeItlbtn * getFccHei());
	}

	public int getSeeItlBtnX() {
		return getSeeItlFldSpc() * 2 + getFccWid();
	}

	public Font getSeeItlBtnFont() {
		return new Font("�L�n������", Font.PLAIN, (int) (getSeeItlBtnHei() * 0.7));
	}

	public Font getSeeItlTxtFont() {
		return new Font("�L�n������", Font.PLAIN, (int) (getSeeItlBtnHei() * 0.625));
	}

	// ----------------------------------------------------------------

	public static final float testComp = 0.65f;

	public int getTestCompHei() {
		return Math.round(testComp * getFccHei());
	}

	// ----------------------------------------------------------------

	public static final int trash = 60;

	public int getTrashLng() {
		return (int) (60 * rate);
	}

	public Rectangle getTrash() {
		return new Rectangle(getTrashLng() / 2, getTrashLng() / 2, getTrashLng(), getTrashLng());
	}

	public Rectangle getMt() {
		return new Rectangle(Math.round(getTrashLng() * 1.5f) + getNmlSpc(), getTrashLng() / 2, getTrashLng(),
				getTrashLng());
	}

	public static final int anmThrowUpTime = 800;
	public static final int anmThrowDownTime = 200;
	public static final int anmThrowPauseTime = 600;

	// ----------------------------------------------------------------

	public static final float seeCardCompHei = 0.3f;

	public int getSeeCardCompHei() {
		return (int) (seeCardCompHei * getSeeItlFldHei());
	}

	// ----------------------------------------------------------------

	public static final int showTeamCard = 400;
	public static final float teamHWRate = (float) 1.399f;

	public static final Color team1_col = Color.RED;
	public static final Color team2_col = new Color(0, 0x99, 0xff);
	public static final Color team3_col = Color.GREEN;

	public int getTeamHei() {
		return Math.round(getTeamWid() * teamHWRate);
	}

	public int getTeamWid() {
		return getChrLng() / 3;
	}

	public Point getTeamLoc(int p, int t) {
		return new Point(Math.round(getChrLng() * 0.85f - getTeamWid()), Math.round(getChrLng() * 0.85f - getTeamHei()));
	}

	// -----------------------------------------------------------------

	public static final float compRate = 0.75f;
	public static final int compHei = 50;

	public int getCompWid() {
		return (int) (scnWidth * compRate);
	}

	public int getCompX() {
		return (int) ((scnWidth - getCompWid()) / 2);
	}

	public int getCompY() {
		return (int) (scnHeight * 0.5f);
	}

	public Rectangle getComp() {
		return new Rectangle(getCompX(), getCompY(), getCompWid(), getCompHei());
	}

	public int getCompHei() {
		return (int) (compHei * rate);
	}

	public Font getCompFont() {
		return new Font("�L�n������", Font.PLAIN, Math.round(getCompHei() * 0.8f));
	}

	// -------------------------------------------------------------------

	public static final int floatTxtShow = 250;
	public static final int floatTxtPause = 1000;
	public static final int floatTxtDis = 1000;

	public Rectangle getFloatComp() {
		return new Rectangle(getFloatCompX(), getFloatCompY(), getCompWid(), getFloatCompHei());
	}

	public int getFloatCompY() {
		return getItlY(seat, 9);
	}

	public int getFloatCompFY() {
		return getFloatCompY() - getFloatCompHei();
	}

	public int getFloatCompHei() {
		return Math.round(getCompHei() * 0.7f);
	}

	public int getFloatCompX() {
		return getCompX();
	}

	public Font getFloatCompFont() {
		return new Font("�L�n������", Font.PLAIN, Math.round(getFloatCompHei() * 0.8f));
	}

	// --------------------------------------------------------------------------------------------------

	public static final int logTxtWid = 40;
	public static final int logTaWid = 200;
	public static final int logBtnWid = 90;
	public static final int logHei = 24;

	public int getLogTxtWid() {
		return (int) (logTxtWid * rate);
	}

	public int getLogTxtX() {
		return (int) (scnWidth - (getLogTxtWid() + getLogTaWid() + getNmlSpc() * 2 + getLogBtnWid())) / 2;
	}

	public int getLogHei() {
		return (int) (logHei * rate);
	}

	public int getUsY() {
		return (scnHeight - (getLogHei() * 3 + getNmlSpc() * 2)) / 2;
	}

	public int getPsY() {
		return getUsY() + getNmlSpc() + getLogHei();
	}

	public int getLogTaWid() {
		return (int) (logTaWid * rate);
	}

	public int getLogTaX() {
		return getLogTxtX() + getNmlSpc() + getLogTxtWid();
	}

	public Rectangle getLogBtn() {
		return new Rectangle(getLogTaX() + getLogTaWid() + getNmlSpc(), getUsY(), getLogBtnWid(),
				getLogHei() * 2 + getNmlSpc());
	}

	public int getLogBtnWid() {
		return (int) (logBtnWid * rate);
	}

	public Rectangle getLogErr(boolean hasConnected) {

		int w = getLogTxtWid() + getLogTaWid() + getNmlSpc() * 2 + getLogBtnWid();
		int h = getLogHei();

		if (hasConnected)
			return new Rectangle(getLogTxtX(), getUsY() + getNmlSpc() * 2 + getLogHei() * 2, w, h);
		else
			return new Rectangle((scnWidth - w) / 2, (scnHeight - h) / 2, w, h);
	}

	// --------------------------------------------------------------------------------------

	public static final int lobSpc = 60;
	public static final int list = 12;
	public static final float roomWid = 0.6f;
	public static final float chatWid = 1.0f - roomWid;
	public static final float lobChatPad = 0.05f;
	public static final float lobChatTaHei = 22;

	public int getLobChatPad() {
		return Math.round(getLobChatWid() * lobChatPad);
	}

	public int getLobSpc() {
		return (int) (lobSpc * rate);
	}

	public int getChatTaHei() {
		return (int) (lobChatTaHei * rate);
	}

	public int getLobListHei() {
		return (int) ((float) (scnHeight - 2 * getLobSpc() - getNmlSpc() * (list - 1)) / list);
	}

	public int getLobRoomWid() {
		return Math.round((scnWidth - 2.5f * getLobSpc()) * roomWid);
	}

	public int getLobChatWid() {
		return Math.round((scnWidth - 2.5f * getLobSpc()) * chatWid);
	}

	public Rectangle getLobChat() {
		return new Rectangle(scnWidth - getLobChatWid() - getLobSpc(), getLobSpc(), getLobChatWid(),
				scnHeight - 2 * getLobSpc());
	}

	public int getLobBtnWid() {
		return Math.round(getLobListHei() * 1.778f);
	}

	public Rectangle getLobBtn(int index) {
		// 0����Ĥ@��
		// -1�k��Ĥ@��
		int x;
		if (index >= 0)
			x = getLobSpc() + (getLobBtnWid() + getNmlSpc()) * index;
		else
			x = getLobSpc() + getLobRoomWid() + getLobBtnWid() * index + getNmlSpc() * (index + 1);
		return new Rectangle(x, scnHeight - getLobSpc() - getLobListHei(), getLobBtnWid(), getLobListHei());
	}

	public Rectangle getRoom(int r) { // r=0��ܲĤ@��
		if (r >= list - 1) {
			new LogicException("���~��r " + r).printStackTrace();
			return null;
		}
		int y = getLobSpc() + (getLobListHei() + getNmlSpc()) * r;
		return new Rectangle(getLobSpc(), y, getLobRoomWid(), getLobListHei());
	}

	// ------------------------------------------------------------

	public static final int roomChrNameHei = 24;
	public static final int roomTxtHei = 36;
	public static final int roomTxtWid = Math.round(roomTxtHei * 4.0f);
	public static final int roomChrSpc = 4;

	public Rectangle getRoomChr(int seat) {

		int i = seat / 3, j = seat % 3;
		int x = getLobSpc() + j * (getRoomChrWid() + getRoomSpcW());
		int y = getLobSpc() + i * (getRoomChrHei() + getRoomSpcH());
		return new Rectangle(x, y, getRoomChrWid(), getRoomChrHei());
	}

	public int getRoomChrSpc() {
		return (int) (roomChrSpc * rate);
	}

	public int getRoomChrWid() {
		return getChrLng();
	}

	public int getRoomChrHei() {
		return getChrLng() + getRoomChrNameHei();
	}

	public int getRoomChrNameHei() {
		return (int) (roomChrNameHei * rate);
	}

	public int getRoomSpcW() {
		return (int) (((float) getLobRoomWid() - 3 * getRoomChrWid() - getLobSpc() * 0.5f) / 2);
	}

	public int getRoomSpcH() {
		return (int) ((scnHeight - getLobSpc() * 3 - getRoomChrHei() * 3 - getRoomTxtHei()) / 2);
	}

	public int getRoomTxtHei() {
		return (int) (roomTxtHei * rate);
	}

	public int getRoomTxtWid() {
		return (int) (roomTxtWid * rate);
	}

	public Rectangle getRoomTxt(int index) {
		int x = 0;
		if (index >= 0) {
			x = getLobSpc() + (getRoomTxtWid() + getNmlSpc()) * index;
		} else {
			x = getLobRoomWid() + getLobSpc() + getRoomTxtWid() * index + getNmlSpc() * (index + 1);
		}
		return new Rectangle(x, scnHeight - getRoomTxtHei() - getLobSpc(), getRoomTxtWid(), getRoomTxtHei());
	}

	// --------------------------------------------

	public int getWinLabelWid(int c) {
		return (int) ((float) (getWinChrLng() - getWinChrSpc() * (c - 1)) / c);
	}

	public static final int winChr = 280;
	public static final int winSpc = 0;

	public int getWinChrLng() {
		return (int) (rate * winChr);
	}

	public int getWinChrWid2() {
		return (getWinChrLng() - getWinChrSpc()) / 2;
	}

	public int getWinChrSpc() {
		return (int) (rate * winSpc);
	}

	public static final int winWid = 400;
	public static final int winHei = 120;

	public Rectangle getWinChr(int i, int t) {
		int w = getWinLabelWid(t) * t + getWinChrSpc() * (t - 1);
		int sx = (scnWidth - w) / 2;
		int x = sx + i * (getWinLabelWid(t) + getWinChrSpc());
		int y = (scnHeight - getWinChrLng()) / 2;
		return new Rectangle(x, y, getWinLabelWid(t), getWinChrLng());
	}

	public int getWinTitleWid() {
		return (int) (winWid * rate);
	}

	public int getWinTitleHei() {
		return (int) (winHei * rate);
	}

	public Rectangle getWin() {
		return new Rectangle((scnWidth - getWinTitleWid()) / 2, (scnHeight + getWinChrLng()) / 2 - getWinTitleHei(),
				getWinTitleWid(), getWinTitleHei());
	}

	// -----------------------------------------------------

	public static final int skFldLng = 180;

	public int getSkFldLng() {
		return (int) (skFldLng * rate);
	}

	public static final int skTime = 1500;
	public static final float skmid = 0.85f;
	public static final int skMidTime = Math.round(skTime * skmid);
	public static final int skRunTime = (skTime - skMidTime) / 2;
	public static final int skFloatTime = 300;
	public static final float skMid = 0.04f;
	public static final float skRun = (1.0f - skMid) / 2;
	public static final int skTxtCount = 3;
	public static final Color skRed1 = Color.RED;
	public static final Color skRed2 = new Color(0xff, 0x80, 0x80);
	public static final Color skRed3 = new Color(0x9f, 0, 0);
	public static final Color skBlue1 = new Color(0, 0x99, 0xff);
	public static final Color skBlue2 = new Color(0x80, 0xcc, 0xff);
	public static final Color skBlue3 = new Color(0, 0x60, 0x9f);

	public int getSkillTxtSh() {
		return Math.round(getChrLng() * 0.40f);
	}

	public int getSkillTxtFh() {
		return Math.round(getChrLng() * 0.30f);
	}

	public Rectangle getSkillTxtF() {
		return new Rectangle(0, (getSkFldLng() + getChrLng()) / 2 - getSkillTxtFh(), getSkFldLng(), getSkillTxtFh());
	}

	public Rectangle getSkillTxtS() {
		return new Rectangle(0, (getSkFldLng() + getChrLng()) / 2 - getSkillTxtSh(), getSkFldLng(), getSkillTxtSh());
	}

	// -----------------------------------------------------

	public static final int anmOpenChrTime = 400;

	// -----------------------------------------------------

	public static final int wt = 8000;

	// -----------------------------------------------------

	public static final int moveUpTime = 400;
	public static final int movePauseTime = 350;
	public static final int moveDownTime = 166;

	// -----------------------------------------------------

	public static final int compText = 15;
	public static final int compTextTitle = 15;
	public static final Color warnText = new Color(242, 27, 248);

	public int getCompText() {
		return (int) (compText * rate);
	}

	public int getCompTextTitle() {
		return (int) (compTextTitle * rate);
	}

	// -----------------------------------------------------

	public static final int gamechatBtnWid = 108;

	public int getGamechatWid() {
		return getHcFld().width - getGamechatBtnWid();
	}

	public Rectangle getGamechat() {
		return new Rectangle(getHcFld().x, getSymFld().y, getGamechatWid(), getHcFld().y - getSymFld().y);
	}

	public int getGamechatBtnWid() {
		return (int) (gamechatBtnWid * rate);
	}

	public Rectangle getGamechatBtn() {
		return new Rectangle(getHcFld().x + getGamechatWid(), getSymFld().y, getGamechatBtnWid(),
				getHcFld().y - getSymFld().y);
	}

	public Dimension getSpeak() {
		return new Dimension((int) (280 * rate), (int) (24 * rate));
	}
}