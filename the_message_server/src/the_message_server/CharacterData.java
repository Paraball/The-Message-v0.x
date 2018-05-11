package the_message_server;

public class CharacterData {

	public static boolean isCover(String chr) {
		switch (chr) {
		case "�Ѱ�":
		case "�Ѻj":
		case "�Ѫ�":
		case "�ª���":
		case "Ķ�q��":
		case "�o�ݭ�":
		case "���K�S�u":
		case "�B��":
		case "�{�F":
		case "����":
			return true;
		case "���j":
		case "�j���k":
		case "�P�R����":
		case "�m��":
		case "§�A�X���H":
		case "�M�W":
		case "�p����":
		case "����":
		case "¾�~����":
		case "縳D":
		case "�ֺ�����":
		case "�����B��":
		case "�p��":
		case "���p�U":
		case "�ǵs�E�E":
			return false;
		default:
			new CharacterException("���s�b������:" + chr).printStackTrace();
			return false;
		}
	}

	public static boolean isMale(Server.Player pl) {
		if (pl.isChrCov) // �\��Τk�n
			return false;
		switch (pl.chr) {
		case "�Ѱ�":
		case "�ª���":
		case "Ķ�q��":
		case "�B��":
		case "�{�F":
		case "����":
		case "���j":
		case "�j���k":
		case "�P�R����":
			return false;
		case "�p��":
		case "�Ѻj":
		case "�Ѫ�":
		case "�o�ݭ�":
		case "���K�S�u":
		case "�m��":
		case "§�A�X���H":
		case "�M�W":
		case "�p����":
		case "����":
		case "¾�~����":
		case "縳D":
		case "�ֺ�����":
		case "�����B��":
		case "���p�U":
		case "�ǵs�E�E":
			return true;
		default:
			new CharacterException("���s�b������:" + pl.chr).printStackTrace();
			return false;
		}
	}
	
	public static boolean isFeMale(Server.Player pl) {
		if (pl.isChrCov) // �\��Τk�n
			return false;
		switch (pl.chr) {
		case "�Ѱ�":
		case "�ª���":
		case "Ķ�q��":
		case "�B��":
		case "�{�F":
		case "����":
		case "���j":
		case "�j���k":
		case "�P�R����":
		case "�p��":
			return true;
		case "�Ѻj":
		case "�Ѫ�":
		case "�o�ݭ�":
		case "���K�S�u":
		case "�m��":
		case "§�A�X���H":
		case "�M�W":
		case "�p����":
		case "����":
		case "¾�~����":
		case "縳D":
		case "�ֺ�����":
		case "�����B��":
		case "���p�U":
		case "�ǵs�E�E":
			return false;
		default:
			new CharacterException("���s�b������:" + pl.chr).printStackTrace();
			return false;
		}
	}

	public static boolean isMaleBySound(Server.Player pl) {
		if (pl.isChrCov) // �\��Τk�n
			return false;
		switch (pl.chr) {
		case "�Ѱ�":
		case "�ª���":
		case "Ķ�q��":
		case "�B��":
		case "�{�F":
		case "����":
		case "���j":
		case "�j���k":
		case "�P�R����":
		case "�p��": // �p�էאּ�k��
			return false;
		case "�Ѻj":
		case "�Ѫ�":
		case "�o�ݭ�":
		case "���K�S�u":
		case "�m��":
		case "§�A�X���H":
		case "�M�W":
		case "�p����":
		case "����":
		case "¾�~����":
		case "縳D":
		case "�ֺ�����":
		case "�����B��":
		case "���p�U":
		case "�ǵs�E�E":
			return true;
		default:
			new CharacterException("���s�b������:" + pl.chr).printStackTrace();
			return false;
		}
	}
}
