package the_message_server;

import java.util.ArrayList;

public class CardData {

	public static String getCardFunc(int id) {
		String s = null;
		switch (id) {
		case 1:
		case 2:
		case 3:
		case 4:
		case 5:
		case 6:
		case 7:
		case 8:
		case 9:
		case 10:
		case 11:
		case 12:
		case 13:
		case 14:
		case 15:
		case 16:
		case 17:
		case 18:
			s = "�ձ�";
			break;
		case 19:
		case 20:
		case 21:
		case 22:
		case 23:
		case 24:
		case 25:
		case 26:
		case 27:
		case 28:
		case 29:
		case 30:
		case 31:
		case 32:
			s = "��w";
			break;
		case 33:
		case 34:
		case 35:
		case 36:
		case 37:
		case 38:
		case 39:
		case 40:
			s = "�ժ����s";
			break;
		case 41:
		case 42:
		case 43:
		case 44:
		case 45:
		case 46:
		case 47:
			s = "�}Ķ";
			break;
		case 48:
		case 49:
		case 50:
		case 51:
		case 52:
		case 53:
			s = "�h�^";
			break;
		case 54:
		case 55:
			s = "�u������";
			break;
		case 56:
		case 57:
		case 58:
		case 59:
		case 60:
		case 61:
		case 62:
		case 63:
			s = "�I��";
			break;
		case 64:
		case 65:
		case 66:
		case 67:
		case 68:
		case 69:
			s = "�N��";
			break;
		case 70:
		case 71:
		case 72:
		case 73:
		case 74:
		case 75:
		case 76:
		case 77:
		case 78:
		case 79:
		case 80:
		case 81:
			s = "�ѯ}";
			break;
		default:
			new CardException("�����T��id: " + id).printStackTrace();
		}
		return s;
	}

	public static String getCardColor(int id) {

		// color������A�@�r

		String s = null;
		if (id <= 18) {
			if (id % 3 == 1)
				s = "r";
			else if (id % 3 == 2)
				s = "b";
			else
				s = "k";
		} else {
			switch (id) {
			case 19:
			case 20:
			case 21:
			case 22:
			case 23:
				s = "r";
				break;
			case 24:
			case 25:
			case 26:
			case 27:
			case 28:
				s = "b";
				break;
			case 29:
			case 30:
			case 31:
			case 32:
				s = "k";
				break;
			case 33:
			case 34:
				s = "r";
				break;
			case 35:
			case 36:
				s = "b";
				break;
			case 37:
			case 38:
			case 39:
			case 40:
				s = "k";
				break;
			case 41:
			case 42:
			case 43:
				s = "r";
				break;
			case 44:
			case 45:
			case 46:
				s = "b";
				break;
			case 47:
				s = "k";
				break;
			case 48:
			case 49:
				s = "r";
				break;
			case 50:
			case 51:
				s = "b";
				break;
			case 52:
			case 53:
				s = "k";
				break;
			case 54:
				s = "r";
				break;
			case 55:
				s = "b";
				break;
			case 56:
				s = "r";
				break;
			case 57:
				s = "b";
				break;
			case 58:
			case 59:
			case 60:
			case 61:
			case 62:
			case 63:
				s = "k";
				break;
			case 64:
				s = "r";
				break;
			case 65:
				s = "b";
				break;
			case 66:
			case 67:
			case 68:
			case 69:
				s = "k";
				break;
			case 70:
			case 71:
			case 72:
				s = "r";
				break;
			case 73:
			case 74:
			case 75:
				s = "b";
				break;
			case 76:
			case 77:
			case 78:
			case 79:
			case 80:
			case 81:
				s = "k";
				break;
			}
		}
		return s;
	}

	public static String getCardName(int id) {
		String s = null;
		if (id <= 18)
			s = String.valueOf(id);
		else {
			switch (id) {
			case 19:
			case 20:
			case 21:
			case 22:
			case 23:
				s = "����w";
				break;
			case 24:
			case 25:
			case 26:
			case 27:
			case 28:
				s = "����w";
				break;
			case 29:
			case 30:
			case 31:
			case 32:
				s = "����w";
				break;
			case 33:
			case 34:
				s = "���ժ�";
				break;
			case 35:
			case 36:
				s = "�Žժ�";
				break;
			case 37:
			case 38:
			case 39:
			case 40:
				s = "�½ժ�";
				break;
			case 41:
			case 42:
			case 43:
				s = "���}Ķ";
				break;
			case 44:
			case 45:
			case 46:
				s = "�ů}Ķ";
				break;
			case 47:
				s = "�¯}Ķ";
				break;
			case 48:
			case 49:
				s = "���h�^";
				break;
			case 50:
			case 51:
				s = "�Űh�^";
				break;
			case 52:
			case 53:
				s = "�°h�^";
				break;
			case 54:
				s = "���u��";
				break;
			case 55:
				s = "�ůu��";
				break;
			case 56:
				s = "���I��";
				break;
			case 57:
				s = "�źI��";
				break;
			case 58:
			case 59:
			case 60:
			case 61:
			case 62:
			case 63:
				s = "�ºI��";
				break;
			case 64:
				s = "���N��";
				break;
			case 65:
				s = "�ſN��";
				break;
			case 66:
			case 67:
			case 68:
			case 69:
				s = "�¿N��";
				break;
			case 70:
			case 71:
			case 72:
				s = "���ѯ}";
				break;
			case 73:
			case 74:
			case 75:
				s = "���ѯ}";
				break;
			case 76:
			case 77:
			case 78:
			case 79:
			case 80:
			case 81:
				s = "���ѯ}";
				break;
			case 99:
				s = "99";
				break;
			}
		}
		return s;
	}

	public static String getCardsByFunc(ArrayList<Integer> cards, String type) {
		String res = "";
		for (int card : cards)
			if (getCardFunc(card).equals(type))
				res += card + ",";
		return res;
	}

	public static String getCardsByColor(ArrayList<Integer> cards, String color) {
		String res = "";
		for (int card : cards)
			if (getCardColor(card).equals(color))
				res += card + ",";
		return res;
	}

	public static String getCards(ArrayList<Integer> cards) {
		String res = "";
		for (int card : cards)
			res += card + ",";
		return res;
	}

	public static String getCardsInColorOrder(String cards) {
		String[] cs = cards.split(",");
		ArrayList<Integer> r = new ArrayList<>(), b = new ArrayList<>(), k = new ArrayList<>();
		for (String c : cs) {
			int id = Integer.parseInt(c);
			switch (getCardColor(id)) {
			case "r":
				r.add(id);
				break;
			case "b":
				b.add(id);
				break;
			case "k":
				k.add(id);
				break;
			}
		}
		return getCards(r) + getCards(b) + getCards(k);
	}

	public static boolean isTrue(int id) {// �������O�_���u
		String color = getCardColor(id);
		if (color.equals("k"))
			return false;
		else
			return true;
	}

	public static boolean isFalse(int[] ids) {// �������O�_���u
		for (int id : ids)
			if (!isTrue(id))
				return true;
		return false;
	}
	
	public static boolean isTrue(int[] ids) {// �������O�_���u
		for (int id : ids)
			if (isTrue(id))
				return true;
		return false;
	}

	public static int getColorCount(ArrayList<Integer> cards, String color) {

		// color�@�ߥΤ���A�@�r

		int num = 0;
		if (color.equals("��"))
			color = "k";
		if (color.equals("r") || color.equals("b") || color.equals("k")) {
			for (int cardId : cards)
				if (getCardColor(cardId).equals(color))
					num++;
		} else if (color.equals("�u")) {
			for (int cardId : cards)
				if (isTrue(cardId))
					num++;
		} else
			new CardException("�ǤJ���~���޼�: " + color).printStackTrace();
		return num;
	}

	public static int getFuncCount(ArrayList<Integer> cards, String func) {
		int num = 0;
		for (int id : cards) {
			if (getCardFunc(id).equals(func))
				num++;
		}
		return num;
	}

	public static String getIntelligenceType(int id) {
		if (id <= 47 && id > 0)
			return "�K�q";
		else if (id <= 55 && id > 47)
			return "�奻";
		else if (id > 55 && id <= 81)
			return "���F";
		else
			new LogicException("�����T���d�P�s��: id = " + id);
		return null;
	}

}
