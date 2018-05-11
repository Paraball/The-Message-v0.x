package the_message_client;

import java.io.*;
import java.util.*;

import javax.swing.ImageIcon;

public class SourceOutput {

	public static void main(String[] args) {
		try {
			output();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static void output() throws FileNotFoundException, IOException {

		ArrayList<ImageIcon> gamecards;
		HashMap<String, ImageIcon> skills;
		HashMap<String, ImageIcon> characters, chrsWin;
		ArrayList<ImageIcon> l, t, d;
		ArrayList<ImageIcon> teams;
		ArrayList<ImageIcon> deadicons;
		HashMap<String, ImageIcon> btns;

		System.out.println("���b�ˬd����");
		File resDocument = new File("res");
		if (!resDocument.exists())
			resDocument.mkdirs();

		// �B��gamecards 0~45
		gamecards = new ArrayList<>();
		System.out.println("���b�Ы�gamecards");
		for (int id = 0; id < 46; id++)
			gamecards.add(new ImageIcon("img/card/" + id + ".png"));
		try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream("res/gamecards.leo"))) {
			oos.writeObject(gamecards);
		}

		// �B�z�ޯ��r�ʵe
		skills = new HashMap<>();
		System.out.println("���b�Ы�skills");
		final String skillpath = "img/�ޯ�";
		String[] skilllist = new File(skillpath).list();
		for (int i = 0; i < skilllist.length; i++)
			if (skilllist[i].endsWith(".png"))
				skills.put(skilllist[i].replace(".png", ""), new ImageIcon(skillpath + "/" + skilllist[i]));
		System.out.println("���b�Ы�skillImages");
		final String skillImagepath = "img/character/�ޯ�";
		String[] skillImagelist = new File(skillImagepath).list();
		for (int i = 0; i < skillImagelist.length; i++)
			if (skillImagelist[i].endsWith(".png"))
				skills.put(skillImagelist[i].replace(".png", ""), new ImageIcon(skillImagepath + "/" + skillImagelist[i]));
		try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream("res/skills.leo"))) {
			oos.writeObject(skills);
		}

		// �B�z�H���Ϥ�
		characters = new HashMap<>();
		System.out.println("���b�Ы�characters");
		final String characterpath = "img/character";
		String[] characterlist = new File(characterpath).list();
		for (int i = 0; i < characterlist.length; i++)
			if (characterlist[i].endsWith(".png"))
				characters.put(characterlist[i].replace(".png", ""), new ImageIcon(characterpath + "/" + characterlist[i]));
		try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream("res/characters.leo"))) {
			oos.writeObject(characters);
		}
		chrsWin = new HashMap<>();
		final String chrsWinpath = "img/character/chrsWin";
		String[] chrsWinlist = new File(chrsWinpath).list();
		for (int i = 0; i < chrsWinlist.length; i++)
			if (chrsWinlist[i].endsWith(".png"))
				chrsWin.put(chrsWinlist[i].replace(".png", ""), new ImageIcon(chrsWinpath + "/" + chrsWinlist[i]));
		try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream("res/chrsWin.leo"))) {
			oos.writeObject(chrsWin);
		}

		// �B�zltd
		l = new ArrayList<>();
		t = new ArrayList<>();
		d = new ArrayList<>();
		System.out.println("���b�Ы�ltds");
		for (int i = 0; i <= 100; i++) {
			l.add(new ImageIcon("img/l/" + i + ".png"));
			t.add(new ImageIcon("img/t/" + i + ".png"));
			d.add(new ImageIcon("img/d/" + i + ".png"));
		}
		try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream("res/l.leo"))) {
			oos.writeObject(l);
		}
		try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream("res/t.leo"))) {
			oos.writeObject(t);
		}
		try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream("res/d.leo"))) {
			oos.writeObject(d);
		}

		// �B�z������D
		teams = new ArrayList<>();
		System.out.println("���b�Ы�teams");
		final String[] teamname = { "�����P", "���Խu", "�x���B", "����o��" };
		for (int i = 0; i < teamname.length; i++)
			teams.add(new ImageIcon("img/idy/" + teamname[i] + ".png"));
		try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream("res/teams.leo"))) {
			oos.writeObject(teams);
		}
		teams.clear();
		for (int i = 1; i <= 5; i++)
			teams.add(new ImageIcon("img/win/" + i + ".png"));
		try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream("res/win.leo"))) {
			oos.writeObject(teams);
		}
		
		// ���`�лx
		deadicons = new ArrayList<>();
		System.out.println("���b�Ы�deadicons");
		final String[] deadtext = { "���`", "��񦺤`", "�x�����`", "��o���`", "�����S", "�x�����S", "��o���S" };
		for (int i = 0; i < deadtext.length; i++)
			deadicons.add(new ImageIcon("img/���`/" + deadtext[i] + ".png"));
		try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream("res/deadicons.leo"))) {
			oos.writeObject(deadicons);
		}

		// �ж��лx
		try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream("res/ready.leo"))) {
			oos.writeObject(new ImageIcon("img/ready.png"));
		}
		try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream("res/closed.leo"))) {
			oos.writeObject(new ImageIcon("img/closed.png"));
		}
		try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream("res/chief.leo"))) {
			oos.writeObject(new ImageIcon("img/chief.png"));
		}
		try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream("res/closeBtnOnExited.leo"))) {
			oos.writeObject(new ImageIcon("img/�������s.png"));
		}
		try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream("res/closeBtnOnEntered.leo"))) {
			oos.writeObject(new ImageIcon("img/�������s�G.png"));
		}

		btns = new HashMap<>();
		System.out.println("���b�Ыث��s");
		final String btnpath = "img/btn";
		String[] btnlist = new File(btnpath).list();
		for (int i = 0; i < btnlist.length; i++)
			if (btnlist[i].endsWith(".png"))
				btns.put(btnlist[i].replace(".png", ""), new ImageIcon(btnpath + "/" + btnlist[i]));
		try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream("res/btns.leo"))) {
			oos.writeObject(btns);
		}

		System.out.println("����");
	}

}
