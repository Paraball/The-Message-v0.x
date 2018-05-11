package the_message_client;

import javax.swing.text.StyledDocument;

public class CharacterData {

	public static StyledDocument getDoc(String chr, Setting sett) {
		Doc doc = new Doc(sett);
		doc.add(4, chr + "\n\n");
		switch(chr){
		case "�Ѱ�":
			doc.add(1, "�N�p�@");
			doc.add(0, "��A�Q�ձ�����w�ɡA�i�H½�}������A�M���G�i�P�C\n");
			doc.add(1, "�����@");
			doc.add(0, "�A�Q�ձ��ɥi�H���f�����C\n");
			doc.add(1, "��ӡ@");
			doc.add(0, "��A���`�ɡA�i�H�������P���¤U�����@�쪱�a�A�L�H����ܨ䤤���T�i�@�������C\n\n");
			doc.add(3, "���K���ȡ@");
			doc.add(0, "��A���`�ɡA�i�ܧA����P�A�䤤���T�i�ΥH�W������d�P�C");
			break;
		case "�Ѻj":
			doc.add(1, "�N�p�@");
			doc.add(0, "��A�Q�ձ�����w�ɡA�i�H½�}������A�M���G�i�P�C\n");
			doc.add(2, "�X�ѡ@");
			doc.add(0, "½�}�A�������P�A�M��\��t�@�쪱�a������P�C\n\n");
			doc.add(3, "���K���ȡ@");
			doc.add(0, "��o�T�i�ΥH�W�����ⱡ���C");
			break;
		case "�Ѫ�":
			doc.add(1, "���O�ӡ@");
			doc.add(0, "��A���d�P�Q�ѯ}�ɡA�i�H½�}������P�A�⤭�i�P�A�M���G�i��P�����N���ǩ�^�Ʈw���C\n");
			doc.add(1, "�y�����~�@");
			doc.add(0, "��A��o�������ɡA�i�H�A��m�@�i��������ۤv���e�A�M��\�񦹨���P�A�æb���N�쪱�a���e�U��m�@�i�����C\n\n");
			doc.add(3, "���K���ȡ@");
			doc.add(0, "��P�����T�i����d�P�M�Ŧ�d�P�C");
			break;
		case "Ķ�q��":
			doc.add(2, "�tĶ�@");
			doc.add(0, "�����ǻ��ɡA�i�H½�}������P�A�M���˵��@�i�ǻ����������A�é�@�i�P�C\n");
			doc.add(1, "�}�x�@");
			doc.add(0, "�����Q�}Ķ�ɡA�A�i�H�\�񦹨���P�C\n\n");
			doc.add(3, "���K���ȡ@");
			doc.add(0, "��o�T�i�ΥH�W���Ŧⱡ���C");
			break;
		case "����":
			doc.add(2, "���å����@");
			doc.add(0, "½�}������P�A�M���t�@�쪱�a���e���@�i������^�P�w���C\n");
			doc.add(1, "�����b�Z�@");
			doc.add(0, "��t�@�쪱�a���`�ɡA�i�H½�}�A�������P�A�M��b�t�@�쪱�a���e��m�̦h��i�������C\n\n");
			doc.add(3, "���K���ȡ@");
			doc.add(0, "�@����Խu���a�M�@��x���B���a���`�C");
			break;
		case "�ª���":
			doc.add(2, "��V����");
			doc.add(0, "½�}������P�A���w�t�@�쪱�a�A�M��i�ܵP�w�����Ĥ@�i�P�A�Y���i�P�O�¦�A�A�i�H�b�L���e��m�̦h�T�i�������A�_�h�A�u���m�@�i�C\n\n");
			doc.add(3, "���K���ȡ@");
			doc.add(0, "�ˤ����@����o�F�T�i�ΥH�W�u���������a���`�C");
			break;
		case "���K�S�u":
			doc.add(2, "ĵı�@");
			doc.add(0, "½�}������P�A�M������ϥΤF�ѯ}�C\n");
			doc.add(1, "���@�@");
			doc.add(0, "�A�i�H��o�t�@�쪱�a���e���@�i�������A�M��\�񦹨���P�C\n\n");
			doc.add(3, "���K���ȡ@");
			doc.add(0, "��o�T�i�ΥH�W�����ⱡ���C");
			break;
		case "�{�F":
			doc.add(1, "�����@");
			doc.add(0, "½�}������P�A�M��N���t�@�쪱�a���e�̦h�T�i�����C\n\n");
			doc.add(3, "���K���ȡ@");
			doc.add(0, "�ˤ����@��S����o�u���������a���`�C");
			break;
		case "�B��":
			doc.add(2, "�����@");
			doc.add(0, "½�}������P�A����@�쪱�a���T�i��P�A�M��L��@�i�P�C\n");
			doc.add(1, "��ۡ@");
			doc.add(0, "��A���`�ɡA�i�H�˵��t�@�쪱�a����P�A�M���䤤�@�i��m�b�@�쪱�a���e�C\n\n");
			doc.add(3, "���K���ȡ@");
			doc.add(0, "�A�����ĤG��ΥH�ᦺ�`�����a�C");
			break;
		case "�o�ݭ�":
			doc.add(1, "�I�W�I�@");
			doc.add(0, "�L�H�^�X�����ǻ��ɡA�i�H½�}������P�A�M��N�@�i�ǻ��m����������m�b�A���e�C\n");
			doc.add(1, "�p���p�@");
			doc.add(0, "��@�쪱�a��o�A�ǥX���u�����ɡA�A�i�H�N���L���e�@�i�������C\n\n");
			doc.add(3, "���K���ȡ@");
			doc.add(0, "��o�T�i�ΥH�W���Ŧⱡ���C");
			break;
		case "���j":
			doc.add(1, "���ݡ@");
			doc.add(0, "��A�ձ��@�쪱�a�ɡA�A�i�H����L���@�i��P�C\n");
			doc.add(1, "�j���@");
			doc.add(0, "��@�쪱�a���`�ɡA�A�H���˵��L���@�i��P�A�M��i�H�N�ӵP�[�J��P�ΰ��������C\n\n");
			doc.add(3, "���K���ȡ@");
			doc.add(0, "��o���i�ΥH�W�������C");
			break;
		case "�j���k":
			doc.add(1, "����@");
			doc.add(0, "�A����P���q�אּ��T�i�P�A�M���ܤ@�i��P��^�P�w���C\n");
			doc.add(2, "�R���@");
			doc.add(0, "½�}�A�������P�A�M��N���G�i�������C\n\n");
			doc.add(3, "���K���ȡ@");
			doc.add(0, "��@��k�ʨ���ŧi�ӧQ�ɡA�A��o���������֩󵥩�@�i�C");
			break;
		case "�P�R����":
			doc.add(1, "��u�ݳơ@");
			doc.add(0, "��A�ϥ���w�νժ����s�ɡA��G�i�P�A�M���ܤ@�i��P��^�P�w���C\n");
			doc.add(1, "�{�M���R�@");
			doc.add(0, "��t�@�쪱�a���`�ɡA�b��½�}�����P�e�A�A�i�H��A�������P���¤U���X�C���A�M��o��L�������P�A�åB�N�@�쪱�a���e���@�i�����P�P�w���Ĥ@�i�P�մ��C");
			break;
		case "�m��":
			doc.add(1, "�ӾU�@");
			doc.add(0, "�A�i�H�b�L�H�^�X���ϥ���w�C\n");
			doc.add(1, "�p�ʡ@");
			doc.add(0, "��A�ϥ���w�ɡA��G�i�P�A�M���ܤ@�i��P�����t�@�쪱�a�C\n");
			doc.add(1, "���ס@");
			doc.add(0, "��A�ŧi�ӧQ�ɡA��ܤ@��k�ʤ@�P�ӧQ�C\n\n");
			doc.add(3, "���K���ȡ@");
			doc.add(0, "��o���i�ΥH�W���u�����C");
			break;
		case "§�A�X���H":
			doc.add(1, "�^���@");
			doc.add(0, "��A��o�������ɡA�A�i�H��G�i�P�C\n");
			doc.add(1, "�Ϭ��@");
			doc.add(0, "2; ��@��k�ʦ��`�ɡA�i�H½�}�A�������P�A�M��N���ۤv�P�o���e�Ҧ����������Ӭ@�Ϧo�C\n\n");
			doc.add(3, "���K���ȡ@");
			doc.add(0, "�@��k�ʫŧi�ӧQ�C");
			break;
		case "�M�W":
			doc.add(1, "�G�C�@");
			doc.add(0, "�A���ձ��M�h�^�i�H����w�ϥΡC\n");
			doc.add(1, "�ݦ�@");
			doc.add(0, "��t�@�쪱�a���`�ɡA�A�i�H��|�i�P�A�M���ܤ@�i��P�����t�@�쪱�a�C\n\n");
			doc.add(3, "���K���ȡ@");
			doc.add(0, "�@����Խu���a�M�@��x���B���a���`�C");
			break;
		case "�p����":
			doc.add(1, "�^������@");
			doc.add(0, "��A��o�������ɡA��@�i�P�A�M��i�H�b�@�쪱�a���e��m�@�i�������C\n\n");
			doc.add(3, "���K���ȡ@");
			doc.add(0, "�@����Խu���a�M�@��x���B���a���`�C");
			break;
		case "����":
			doc.add(1, "�����äM�@");
			doc.add(0, "��t�@�쪱�a��o�A�ǥX���u�����ɡA�A�i�H�b�L���e��m�@�i�������C\n");
			doc.add(1, "�B�w�c��@");
			doc.add(0, "��A��o�u�����ɡA�A�i�H��@�i�P�C\n\n");
			doc.add(3, "���K���ȡ@");
			doc.add(0, "��o�T�i�ΥH�W���Ŧⱡ���C");
			break;
		case "¾�~����":
			doc.add(1, "�w�ѡ@");
			doc.add(0, "�A���ժ����s�i�H��@��w�ϥΡC\n");
			doc.add(1, "�s���@");
			doc.add(0, "��@�쪱�a��o�A�ǥX���������ɡA�A��@�i�P�A�M��i�H�b�L���e�A��m�@�i�������C\n\n");
			doc.add(3, "���K���ȡ@");
			doc.add(0, "�ˤ����t�@�쪱�a�����ĤG��ΥH�ᦺ�`�����a�C");
			break;
		case "縳D":
			doc.add(1, "�n�F����@");
			doc.add(0, "�\�񦹨���P�A�M�ᬰ�ϥΤF�ժ����s�C\n");
			doc.add(1, "�u�ĵL���@");
			doc.add(0, "�Y�A�S����P�A�i�H½�}������P�A�M���@�i�P�C\n\n");
			doc.add(3, "���K���ȡ@");
			doc.add(0, "��@�쪱�a�ŧi�ӧQ�ɡA�S�����a���`�C�A���ӧQ�|�ɭP�L���ŧi����");
			break;
		case "�ֺ�����":
			doc.add(1, "���S�@");
			doc.add(0, "�����ǻ��ɡA�i�H���@�i�¦��P�A�M��½�}�@�i�ǻ������K�q�A�Y���������A�h��G�i�P�C\n");
			doc.add(1, "�u�ۡ@");
			doc.add(0, "��@�쪱�a�ϥθձ��ɡA�A�i�H��@�i��P���˵��Ӹձ��C\n\n");
			doc.add(3, "���K���ȡ@");
			doc.add(0, "��o�T�i�ΥH�W�����ⱡ��");
			break;
		case "�����B��":
			doc.add(1, "�j�𦳦ա@");
			doc.add(0, "��A�ձ��@�쪱�a�ɡA�A�i�H��G�i�P�A�M���ܤ@�i��P��^�P�w���C\n");
			doc.add(1, "���s���@");
			doc.add(0, "������F�A�ɡA�A�i�H���@�i��P�A�M��N�ӱ����P�P�w�����Ĥ@�i�P�մ��C\n\n");
			doc.add(3, "���K���ȡ@");
			doc.add(0, "��o�T�i�ΥH�W�����ⱡ��");
			break;
		case "�p��":
			doc.add(1, "��ˡ@");
			doc.add(0, "������J�����k�ʤ]�����k�ʡC\n");
			doc.add(2, "���R�@");
			doc.add(0, "��A���|�i��P�浹�@�쪱�a�A�M����o�L���e���@�i�����C\n");
			doc.add(1, "���S�@");
			doc.add(0, "��A��o���i�ΥH�W�������ɡA�A�鱼�o���C���C\n\n");
			doc.add(3, "���K���ȡ@");
			doc.add(0, "�A�����Ĥ@�즺�`�����a�A�μ��Խu�P�x���B�䤤�@������C");
			break;
		case "���p�U":
			doc.add(1, "����@");
			doc.add(0, "�A���¦��P�i�H��@��w�ϥΡC\n");
			doc.add(1, "���ѡ@");
			doc.add(0, "��@�쪱�a��o�A�ǥX���������ɡA�A�i�H��L���e���@�i�u�����[�J����N�@�쪱�a����P���C\n\n");
			doc.add(3, "���K���ȡ@");
			doc.add(0, "��P�����T�i����d�P�M�Ŧ�d�P�C");
			break;
		case "�ǵs�E�E":
			doc.add(1, "���ѡ@");
			doc.add(0, "��t�@�쪱�a��o�A�ǥX���������ɡA�A�i�H������N�@�쪱�a���@�i��P�C\n");
			doc.add(1, "���ѡ@");
			doc.add(0, "��A��o�ǥX���������ɡA�A�i�H������N�@�쪱�a���@�i��P�C\n\n");
			doc.add(3, "���K���ȡ@");
			doc.add(0, "��o�E�i�ΥH�W����P�C");
			break;
		}
		return doc.get();
	}
}
