package the_message_server;

public class StringFormatter {

	public static char numCh(int num) {
		switch (num) {
		case 1:
			return '�@';
		case 2:
			return '�G';
		case 3:
			return '�T';
		case 4:
			return '�|';
		case 5:
			return '��';
		case 6:
			return '��';
		case 7:
			return '�C';
		case 8:
			return '�K';
		case 9:
			return '�E';
		case 10:
			return '�Q';
		}
		if (num < 1 || num > 10)
			new LogicException("�Ӥj�ΤӤp���Ʀr: " + num).printStackTrace();
		return ' ';
	}
}
