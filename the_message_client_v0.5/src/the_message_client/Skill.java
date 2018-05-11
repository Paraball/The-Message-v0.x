package the_message_client;

import javax.swing.text.StyledDocument;

public class Skill {

	public static final int count = 149;

	public static boolean isRed(int id) {
		if (id < 100 || id > count)
			new LogicException("���~���ޯ�ID: " + id).printStackTrace();
		switch (id) {
		case 100:
		case 101:
		case 102:
		case 103:
		case 105:
		case 106:
		case 108:
		case 110:
		case 113:
		case 114:
		case 116:
		case 118:
		case 119:
		case 120:
		case 121:
		case 123:
		case 124:
		case 125:
		case 126:
		case 127:
		case 128:
		case 129:
		case 130:
		case 131:
		case 132:
		case 133:
		case 134:
		case 135:
		case 136:
		case 137:
		case 138:
		case 139:
		case 140:
		case 141:
		case 142:
		case 143:
		case 145:
		case 146:
		case 147:
		case 148:
		case 149:
		case 151:
		case 152:
		case 153:
		case 154:
		case 155:
		case 157:
		case 158:
		case 159:
			return true;
		}
		return false;
	}

	public static String getSkillName(int id) {
		switch (id) {
		case 100:
		case 103:
			return "�N�p";
		case 101:
			return "����";
		case 102:
			return "���";
		case 104:
			return "�X��";
		case 105:
			return "���O��";
		case 106:
			return "�y�����~";
		case 107:
			return "�tĶ";
		case 108:
			return "�}�x";
		case 109:
			return "���å���";
		case 110:
			return "�����b�Z";
		case 111:
			return "��V����";
		case 112:
			return "ĵı";
		case 113:
			return "���@";
		case 114:
			return "����";
		case 115:
			return "����";
		case 116:
			return "���";
		case 117:
			return "�I�W�I";
		case 118:
			return "�p���p";
		case 119:
			return "����";
		case 120:
			return "�j��";
		case 121:
			return "���";
		case 122:
			return "�R��";
		case 123:
			return "��u�ݳ�";
		case 124:
			return "�{�M���R";
		case 125:
			return "�ӾU";
		case 126:
			return "�p��";
		case 127:
			return "����";
		case 128:
			return "�^��";
		case 129:
			return "�Ϭ�";
		case 130:
			return "�G�C";
		case 131:
			return "�ݦ�";
		case 132:
			return "�^������";
		case 133:
			return "�����äM";
		case 134:
			return "�B�w�c��";
		case 135:
			return "�w��";
		case 136:
			return "�s��";
		case 137:
			return "�n�F����";
		case 138:
			return "�u�ĵL��";
		case 139:
			return "���S";
		case 140:
			return "�u��";
		case 141:
			return "�j�𦳦�";
		case 142:
			return "���s���";
		case 143:
			return "���";
		case 144:
			return "���R";
		case 145:
			return "���S";
		case 146:
			return "����";
		case 147:
			return "����";
		case 148:
			return "����";
		case 149:
			return "����";
		case 150:
			return "���v�B��";
		case 151:
			return "����d��";
		case 152:
			return "�w�I�ٰ�";
		case 153:
			return "�j�U�d��";
		case 154:
			return "�ƼC���p";
		case 155:
			return "����P��";
		case 156:
			return "���ᱵ��";
		case 157:
			return "���Ͳ��T";
		case 158:
			return "�ߴ��g��";
		case 159:
			return "����ǻ�";
		}
		new LogicException("���~��ID: " + id).printStackTrace();
		return null;
	}

	public static String getChr(int id) {
		switch (id) {
		case 100:
		case 101:
		case 102:
			return "�Ѱ�";
		case 103:
		case 104:
			return "�Ѻj";
		case 105:
		case 106:
			return "�Ѫ�";
		case 107:
		case 108:
			return "Ķ�q��";
		case 109:
		case 110:
			return "����";
		case 111:
			return "�ª���";
		case 112:
		case 113:
			return "���K�S�u";
		case 114:
			return "�{�F";
		case 115:
		case 116:
			return "�B��";
		case 117:
		case 118:
			return "�o�ݭ�";
		case 119:
		case 120:
			return "���j";
		case 121:
		case 122:
			return "�j���k";
		case 123:
		case 124:
			return "�P�R����";
		case 125:
		case 126:
		case 127:
			return "�m��";
		case 128:
		case 129:
			return "§�A�X���H";
		case 130:
		case 131:
			return "�M�W";
		case 132:
			return "�p����";
		case 133:
		case 134:
			return "����";
		case 135:
		case 136:
			return "¾�~����";
		case 137:
		case 138:
			return "縳D";
		case 139:
		case 140:
			return "�ֺ�����";
		case 141:
		case 142:
			return "�����B��";
		case 143:
		case 144:
		case 145:
			return "�p��";
		case 146:
		case 147:
			return "���p�U";
		case 148:
		case 149:
			return "�ǵs�E�E";
		case 150:
		case 151:
			return "��d��";
		case 152:
		case 153:
			return "�X���s";
		case 154:
		case 155:
			return "�����y��";
		case 156:
		case 157:
			return "ĳ��";
		case 158:
		case 159:
			return "�d���p�j";
		}
		new LogicException("���~��ID: " + id).printStackTrace();
		return null;
	}

	public static boolean getRed(String txt) {
		for (int i = 100; i <= count; i++)
			if (isRed(i))
				return true;
		return false;
	}

	public static StyledDocument getDoc(int id, Setting sett) {
		Doc doc = new Doc(sett);
		switch (id) {
		case 100:
		case 103:
			doc.add(1, "�N�p�@");
			doc.add(0, "��A�Q�ձ�����w�ɡA�i�H½�}������A�M���G�i�P�C");
			break;
		case 101:
			doc.add(1, "�����@");
			doc.add(0, "�A�Q�ձ��ɥi�H���f�����C");
			break;
		case 102:
			doc.add(1, "��ӡ@");
			doc.add(0, "��A���`�ɡA�i�H�������P���¤U�����@�쪱�a�A�L�H����ܨ䤤���T�i�@�������C");
			break;
		case 104:
			doc.add(2, "�X�ѡ@");
			doc.add(0, "½�}�A�������P�A�M��\��t�@�쪱�a������P�C");
			break;
		case 105:
			doc.add(1, "���O�ӡ@");
			doc.add(0, "��A���d�P�Q�ѯ}�ɡA�i�H½�}������P�A�⤭�i�P�A�M���G�i��P�����N���ǩ�^�Ʈw���C");
			break;
		case 106:
			doc.add(1, "�y�����~�@");
			doc.add(0, "��A��o�������ɡA�i�H�A��m�@�i��������ۤv���e�A�M��\�񦹨���P�A�æb���N�쪱�a���e�U��m�@�i�����C");
			break;
		case 107:
			doc.add(2, "�tĶ�@");
			doc.add(0, "�����ǻ��ɡA�i�H½�}������P�A�M���˵��@�i�ǻ����������A�é�@�i�P�C");
			break;
		case 108:
			doc.add(1, "�}�x�@");
			doc.add(0, "�����Q�}Ķ�ɡA�A�i�H�\�񦹨���P�C");
			break;
		case 109:
			doc.add(2, "���å����@");
			doc.add(0, "½�}������P�A�M���t�@�쪱�a���e���@�i������^�P�w���C");
			break;
		case 110:
			doc.add(1, "�����b�Z�@");
			doc.add(0, "��t�@�쪱�a���`�ɡA�i�H½�}�A�������P�A�M��b�t�@�쪱�a���e��m�̦h��i�������C");
			break;
		case 111:
			doc.add(2, "��V����");
			doc.add(0, "½�}������P�A���w�t�@�쪱�a�A�M��i�ܵP�w�����Ĥ@�i�P�A�Y���i�P�O�¦�A�A�i�H�b�L���e��m�̦h�T�i�������A�_�h�A�u���m�@�i�C");
			break;
		case 112:
			doc.add(2, "ĵı�@");
			doc.add(0, "½�}������P�A�M������ϥΤF�ѯ}�C");
			break;
		case 113:
			doc.add(1, "���@�@");
			doc.add(0, "�A�i�H��o�t�@�쪱�a���e���@�i�������A�M��\�񦹨���P�C");
			break;
		case 114:
			doc.add(1, "�����@");
			doc.add(0, "½�}������P�A�M��N���t�@�쪱�a���e�̦h�T�i�����C");
			break;
		case 115:
			doc.add(2, "�����@");
			doc.add(0, "½�}������P�A����@�쪱�a���T�i��P�A�M��L��@�i�P�C");
			break;
		case 116:
			doc.add(1, "��ۡ@");
			doc.add(0, "��A���`�ɡA�i�H�˵��t�@�쪱�a����P�A�M���䤤�@�i��m�b�@�쪱�a���e�C");
			break;
		case 117:
			doc.add(1, "�I�W�I�@");
			doc.add(0, "�L�H�^�X�����ǻ��ɡA�i�H½�}������P�A�M��N�@�i�ǻ��m����������m�b�A���e�C");
			break;
		case 118:
			doc.add(1, "�p���p�@");
			doc.add(0, "��@�쪱�a��o�A�ǥX���u�����ɡA�A�i�H�N���L���e�@�i�������C");
			break;
		case 119:
			doc.add(1, "���ݡ@");
			doc.add(0, "��A�ձ��@�쪱�a�ɡA�A�i�H����L���@�i��P�C");
			break;
		case 120:
			doc.add(1, "�j���@");
			doc.add(0, "��@�쪱�a���`�ɡA�A�H���˵��L���@�i��P�A�M��i�H�N�ӵP�[�J��P�ΰ��������C");
			break;
		case 121:
			doc.add(1, "����@");
			doc.add(0, "�A����P���q�אּ��T�i�P�A�M���ܤ@�i��P��^�P�w���C");
			break;
		case 122:
			doc.add(2, "�R���@");
			doc.add(0, "½�}�A�������P�A�M��N���G�i�������C");
			break;
		case 123:
			doc.add(1, "��u�ݳơ@");
			doc.add(0, "��A�ϥ���w�νժ����s�ɡA��G�i�P�A�M���ܤ@�i��P��^�P�w���C");
			break;
		case 124:
			doc.add(1, "�{�M���R�@");
			doc.add(0, "��t�@�쪱�a���`�ɡA�b��½�}�����P�e�A�A�i�H��A�������P���¤U���X�C���A�M��o��L�������P�A�åB�N�@�쪱�a���e���@�i�����P�P�w���Ĥ@�i�P�մ��C");
			break;
		case 125:
			doc.add(1, "�ӾU�@");
			doc.add(0, "�A�i�H�b�L�H�^�X���ϥ���w�C");
			break;
		case 126:
			doc.add(1, "�p�ʡ@");
			doc.add(0, "��A�ϥ���w�ɡA��G�i�P�A�M���ܤ@�i��P�����t�@�쪱�a�C");
			break;
		case 127:
			doc.add(1, "���ס@");
			doc.add(0, "��A�ŧi�ӧQ�ɡA��ܤ@��k�ʤ@�P�ӧQ�C");
			break;
		case 128:
			doc.add(1, "�^���@");
			doc.add(0, "��A��o�������ɡA�A�i�H��G�i�P�C");
			break;
		case 129:
			doc.add(1, "�Ϭ��@");
			doc.add(0, "2; ��@��k�ʦ��`�ɡA�i�H½�}�A�������P�A�M��N���ۤv�P�o���e�Ҧ����������Ӭ@�Ϧo�C");
			break;
		case 130:
			doc.add(1, "�G�C�@");
			doc.add(0, "�A���ձ��M�h�^�i�H����w�ϥΡC");
			break;
		case 131:
			doc.add(1, "�ݦ�@");
			doc.add(0, "��t�@�쪱�a���`�ɡA�A�i�H��|�i�P�A�M���ܤ@�i��P�����t�@�쪱�a�C");
			break;
		case 132:
			doc.add(1, "�^������@");
			doc.add(0, "��A��o�������ɡA��@�i�P�A�M��i�H�b�@�쪱�a���e��m�@�i�������C");
			break;
		case 133:
			doc.add(1, "�����äM�@");
			doc.add(0, "��t�@�쪱�a��o�A�ǥX���u�����ɡA�A�i�H�b�L���e��m�@�i�������C");
			break;
		case 134:
			doc.add(1, "�B�w�c��@");
			doc.add(0, "��A��o�u�����ɡA�A�i�H��@�i�P�C");
			break;
		case 135:
			doc.add(1, "�w�ѡ@");
			doc.add(0, "�A���ժ����s�i�H��@��w�ϥΡC");
			break;
		case 136:
			doc.add(1, "�s���@");
			doc.add(0, "��@�쪱�a��o�A�ǥX���������ɡA�A��@�i�P�A�M��i�H�b�L���e�A��m�@�i�������C");
			break;
		case 137:
			doc.add(1, "�n�F����@");
			doc.add(0, "�\�񦹨���P�A�M������ϥΤF�ժ����s�C");
			break;
		case 138:
			doc.add(1, "�u�ĵL���@");
			doc.add(0, "�Y�A�S����P�A�i�H½�}������P�A�M���@�i�P�C");
			break;
		case 139:
			doc.add(1, "���S�@");
			doc.add(0, "�����ǻ��ɡA�i�H���@�i�¦��P�A�M��½�}�@�i�ǻ������K�q�A�Y���������A�h��G�i�P�C");
			break;
		case 140:
			doc.add(1, "�u�ۡ@");
			doc.add(0, "��@�쪱�a�ϥθձ��ɡA�A�i�H��@�i��P���˵��Ӹձ��C");
			break;
		case 141:
			doc.add(1, "�j�𦳦ա@");
			doc.add(0, "��A�ձ��@�쪱�a�ɡA�A�i�H��G�i�P�A�M���ܤ@�i��P��^�P�w���C");
			break;
		case 142:
			doc.add(1, "���s���@");
			doc.add(0, "������F�A�ɡA�A�i�H���@�i��P�A�M��N�ӱ����P�P�w�����Ĥ@�i�P�մ��C");
			break;
		case 143:
			doc.add(1, "��ˡ@");
			doc.add(0, "������J�����k�ʤ]�����k�ʡC");
			break;
		case 144:
			doc.add(2, "���R�@");
			doc.add(0, "��A���|�i��P�浹�@�쪱�a�A�M����o�L���e���@�i�����C");
			break;
		case 145:
			doc.add(1, "���S�@");
			doc.add(0, "��A��o���i�ΥH�W�������ɡA�A�鱼�o���C���C");
			break;
		case 146:
			doc.add(1, "����@");
			doc.add(0, "�A���¦��P�i�H��@��w�ϥΡC");
			break;
		case 147:
			doc.add(1, "���ѡ@");
			doc.add(0, "��@�쪱�a��o�A�ǥX���������ɡA�A�i�H��L���e���@�i�u�����[�J����N�@�쪱�a����P���C");
			break;
		case 148:
			doc.add(1, "���ѡ@");
			doc.add(0, "��t�@�쪱�a��o�A�ǥX���������ɡA�A�i�H������N�@�쪱�a���@�i��P�C");
			break;
		case 149:
			doc.add(1, "���ѡ@");
			doc.add(0, "��A��o�ǥX���������ɡA�A�i�H������N�@�쪱�a���@�i��P�C");
			break;
		}
		return doc.get();
	}

	public StyledDocument getDoc(String chr, Setting sett) {
		Doc doc = new Doc(sett);

		return doc.get();
	}

	public static boolean isSkill(String skillName) {
		switch (skillName) {
		case "�N�p":
		case "����":
		case "���":
		case "�X��":
		case "���O��":
		case "�y�����~":
		case "�tĶ":
		case "�}�x":
		case "���å���":
		case "�����b�Z":
		case "��V����":
		case "ĵı":
		case "���@":
		case "����":
		case "����":
		case "���":
		case "�I�W�I":
		case "�p���p":
		case "����":
		case "�j��":
		case "���":
		case "�R��":
		case "��u�ݳ�":
		case "�{�M���R":
		case "�ӾU":
		case "�p��":
		case "����":
		case "�^��":
		case "�Ϭ�":
		case "�G�C":
		case "�ݦ�":
		case "�^������":
		case "�����äM":
		case "�B�w�c��":
		case "�w��":
		case "�s��":
		case "�n�F����":
		case "�u�ĵL��":
		case "���S":
		case "�u��":
		case "�j�𦳦�":
		case "���s���":
		case "���":
		case "���R":
		case "���S":
		case "����":
		case "����":
		case "����":
		case "����":
		case "���v�B��":
		case "����d��":
		case "�w�I�ٰ�":
		case "�j�U�d��":
		case "�ƼC���p":
		case "����P��":
		case "���ᱵ��":
		case "���Ͳ��T":
		case "�ߴ��g��":
		case "����ǻ�":
			return true;
		}
		return false;
	}

	public static void main(String[] args) {
		for (int i = 100; i <= 159; i++) {
			System.out.println("case \"" + getSkillName(i) + "\":");
		}
	}

}
