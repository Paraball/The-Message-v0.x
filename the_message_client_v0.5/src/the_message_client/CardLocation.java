package the_message_client;

public class CardLocation {
	// �����O�@�����ʵP����T

	int who, count;
	String where;

	public CardLocation(int who, String where, int count) {
		this.who = who;
		this.where = where; // r,b,k,h,i
		this.count = count; // ��s��ƶq -1��ܤ���
	}

}
