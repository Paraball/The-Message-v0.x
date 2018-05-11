package the_message_client;

import java.applet.AudioClip;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.net.*;
import java.util.*;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JLayeredPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.JTextPane;
import javax.swing.border.TitledBorder;

@SuppressWarnings("serial")
public class Client extends JFrame {

	JLayeredPane c;
	Container $c;
	Socket sc;
	Setting sett;
	PrintWriter pw;
	BufferedReader br;

	AudioClip bg_au, bo_au, pa_au, useCard_au, drawCard_au;

	// ------------------------------------------------------
	/*
	 * sit�ܼƨϥλ����p�U �e�@�X: 0�n�J�e�� 1�j�U�e�� 2�ж��e�� 3�P���e�� 4�p���e�� �e��X: 30��ܨ��⪬�A 31���ݥL�H��ܨ��⪬�A
	 * 32�C���ǳƪ��A(�o�����P�B�o��l��P) 33�εP�e���q 34�εPI 35��ܱ������q 36�����ǻ����q 37������F���q 38��ܱ������q
	 * 39�εPII �e�T�X 3X+�ޯ�ާ@�����A 3X0�ۤv�^�X���ݶ��q 3X-�L�H�^�X���ݶ��q 3X1��w��ܤH�����A 3X2�ժ����s��ܤH�����A
	 * 3X3�N����ܤH���α������A 3X4�ձ���ܪ��a���A 3X5�ѯ}��ܥd�P���A
	 */
	String sit = "0";
	// ------------------------------------------------------
	JLabel bg_lb, hcFld_lb, symFld_lb, timeline_lb, compS_lb; // �I���ϡB��P���B�ޯ����BŪ���B�i�ܨ�����
	JPanel chooChr_pn; // �﨤���
	GameButton rec_btn/* ���� */, nRec_btn/* ���� */, ok_btn/* �T�{ */, ccl_btn/* ���� */, undo_btn/* ���� */, ctn_btn/* �~�� */,
			lev_btn/* �U�� */, nLev_btn/* �ѰU */, skp_btn/* ���L */;
	TrashLabel trash_lb, mt_lb;
	IntelligenceLabel itl_lb;
	ItlPanel itl_pn;
	LotteryPanel lp;
	TestPanel test_pn;
	SeeCardPanel see_pn;
	SeeItlPanel seeItl_pn;

	IdealLabel usnm_lb, pswd_lb, logErr_lb;
	IdealLabel login_btn;
	IdealField usnm_ta, gamechat_ta;
	IdealLabel gamechatBtn_lb;
	IdealPassword pswd_ta;
	JPanel gamechatSound_pn;
	ArrayList<JLabel> win_lb;
	JLabel winTeam_lb;
	ChatPane chat_pn;
	int floatNum = 0;
	HashMap<Integer, SkillLabel> skills = new HashMap<>();
	ArrayList<Integer> selects;
	ArrayList<String> list;
	int sMin, sMax;
	boolean sMust;
	SkillComp skillComp_pn;
	CharacterComp chrComp_pn;

	// --------------------------------------------------------
	NormalListener nl = new NormalListener();
	HandcardListener hl = new HandcardListener();
	FunccardListener fl = new FunccardListener();
	CharacterListener cl = new CharacterListener();
	CharacterMoveListener cml = new CharacterMoveListener();
	ButtonListener bl = new ButtonListener();
	ItlCardListener il = new ItlCardListener(true), il$ = new ItlCardListener(false);
	ShowCardListener sl = new ShowCardListener();
	ImformListener ml = new ImformListener();
	ClosePanelListener cll = new ClosePanelListener();
	IdealListener dl = new IdealListener();
	RoomChrLabelListener rl = new RoomChrLabelListener();
	SkillListener skl = new SkillListener();
	SkillMoveListener sml = new SkillMoveListener();
	NormalMoveListener nml = new NormalMoveListener();
	ChatKeyListener ckl = new ChatKeyListener();
	// -------------------------------------------------------
	Game game;
	Lobby lobby;
	Room room;
	// --------------------------------------------------------
	int ttt;
	boolean timeflag;
	Runnable time_run = () -> {
		int time = ttt;
		timeflag = true;
		while (timeflag) {
			if ((time -= Setting.anmSpc) > 0) {
				timeline_lb.setSize(Math.round((float) sett.getTimelineWid() * time / ttt), sett.getTimelineHei());
				try {
					Thread.sleep(Setting.anmSpc);
				} catch (InterruptedException e)

				{
					e.printStackTrace();
				}
			} else
				break;
		}
		timeline_lb.setSize(0, sett.getTimelineHei());
		if (timeflag)
			synchronized (sit) {
				print(Thread.currentThread().getName());
			}
	};

	Thread time_th = new Thread();

	boolean isSelectingChr; // ���b��ܨ���
	boolean isSelectingCard; // ���b����ѯ}�ؼ�
	int isSelectingItl = 0; // ���b��ܱ��� 0���i 1��@ 2�h�i
	int isSelectingHc = 0; // ���b��ܤ�P 0���i 1�X�P���q 2��@��P 3�h�i��P 100�ޯत
	boolean isQueuing = false; // ���b����d�P

	class WindowListener extends WindowAdapter {
		public void windowClosing(WindowEvent e) {
			try {
				if (pw != null) {
					pw.close();
					System.out.println("������X��y");
				}
				if (br != null)
					try {
						br.close();
						System.out.println("����Ū�J��y");
					} catch (IOException e1) {
						e1.printStackTrace();
					}
				if (sc != null)
					try {
						sc.close();
						System.out.println("�����s��");
					} catch (IOException e1) {
						e1.printStackTrace();
					}
			} catch (Exception e2) {
				e2.printStackTrace();
			}
			System.exit(0);
		}
	}

	public static Object in(String path) {
		try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream("image/" + path + ".leo"))) {
			try {
				return ois.readObject();
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

	public static void main(String[] args) {
		Client cl = null;
		try {
			cl = new Client();
		} catch (Exception e) {
			if (cl != null) {
				e.printStackTrace();
				if (cl.pw != null)
					cl.pw.close();
				if (cl.br != null)
					try {
						cl.br.close();
					} catch (IOException e1) {
						e1.printStackTrace();
					}
				if (cl.sc != null)
					try {
						cl.sc.close();
					} catch (IOException e1) {
						e1.printStackTrace();
					}
			}
		}
	}

	public Client() {
		super("���n��C");
		Dimension d = java.awt.Toolkit.getDefaultToolkit().getScreenSize();
		addWindowListener(new WindowListener());
		dispose();
		setVisible(true);
		sett = new Setting(d.width, d.height - 60);
		setSize(sett.scnWidth, sett.scnHeight + 30);
		setLocationRelativeTo(null);
		setResizable(false);

		$c = getContentPane();
		$c.setBackground(Setting.bg);
		c = getLayeredPane();
		logErr_lb = new IdealLabel("���J��", null);
		logErr_lb.setBounds(sett.getLogErr(false));
		logErr_lb.setForeground(Color.RED);
		logErr_lb.setHorizontalAlignment(JLabel.CENTER);
		c.add(logErr_lb, (Integer) 1);

		try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream("setting.dll"))) {
			Setting s = (Setting) ois.readObject();
			if (sett.rate != s.rate) { // �g�J�s����
				new Initializer(sett, logErr_lb).initialize();
				try {
					this.wait();
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		} catch (FileNotFoundException | InvalidClassException e1) {
			e1.printStackTrace();
			try {
				new Initializer(sett, logErr_lb).initialize();
				try {
					this.wait();
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			} catch (FileNotFoundException e2) {
				logErr_lb.setText(e2.getMessage());
				e2.printStackTrace();
			} catch (IOException e2) {
				logErr_lb.setText(e2.getMessage());
				e2.printStackTrace();
			} catch (ClassNotFoundException e2) {
				e2.printStackTrace();
			}
		} catch (IOException e) {
			e.printStackTrace();
			logErr_lb.setText(e.getMessage());
			System.exit(0);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
			logErr_lb.setText(e.getMessage());
			System.exit(0);
		}

		bg_au = getAudio("�j�U");
		bo_au = getAudio("�i");
		pa_au = getAudio("��");
		drawCard_au = getAudio("��P");
		useCard_au = getAudio("�X�P");
		usnm_lb = new IdealLabel("�b��", null);
		pswd_lb = new IdealLabel("�K�X", null);
		login_btn = new IdealLabel("�n�J", "!login");
		usnm_ta = new IdealField();
		pswd_ta = new IdealPassword();
		usnm_lb.setBounds(sett.getLogTxtX(), sett.getUsY(), sett.getLogTxtWid(), sett.getLogHei());
		pswd_lb.setBounds(sett.getLogTxtX(), sett.getPsY(), sett.getLogTxtWid(), sett.getLogHei());
		usnm_ta.setBounds(sett.getLogTaX(), sett.getUsY(), sett.getLogTaWid(), sett.getLogHei());
		pswd_ta.setBounds(sett.getLogTaX(), sett.getPsY(), sett.getLogTaWid(), sett.getLogHei());
		login_btn.fontr = 0.65;
		login_btn.setBounds(sett.getLogBtn());
		login_btn.setHorizontalAlignment(JLabel.CENTER);

		bg_au.loop();

		try {
			sc = new Socket("themessage.ddns.net", 10222);
			br = new BufferedReader(new InputStreamReader(sc.getInputStream()));
			pw = new PrintWriter(new BufferedWriter(new OutputStreamWriter(sc.getOutputStream())));

			// Ū�J�j�j��----------------------------------------------
			Runnable logic_th = () -> {
				try {
					logic();
				} catch (Exception e) {
					e.printStackTrace();
					if (pw != null)
						pw.close();
					if (br != null)
						try {
							br.close();
						} catch (Exception e1) {
							e1.printStackTrace();
						}
					if (sc != null)
						try {
							sc.close();
						} catch (Exception e1) {
							e1.printStackTrace();
						}
				}
			};
			new Thread(logic_th).start();
		} catch (ConnectException e) {
			e.printStackTrace();
			logErr_lb.setText("�L�k�P���A���s�u");
		} catch (UnknownHostException e) {
			e.printStackTrace();
			logErr_lb.setText("���A���ݲ��`�A�p���}�o��");
		} catch (IOException e) {
			e.printStackTrace();
			logErr_lb.setText("�L�k�P���A���s�u");
		}

	}

	public void print(String ms) {
		pw.println(ms);
		System.out.println("�ǰe�T���G" + ms);
		pw.flush();
	}

	public void print(ArrayList<String> mess) {
		for (String ms : mess) {
			pw.println(ms);
			System.out.println("�ǰe�T���G" + ms);
		}
		pw.flush();
	}

	// --------------------------------------------------------
	public void logic() throws IOException {
		String mess = null;
		while (true) {
			if ((mess = br.readLine()) != null) {
				System.out.println("Ū�J�T���G " + mess);
				String[] ms = mess.split(":");
				switch (ms[0]) {
				case "�����n��":
					getAudio(ms[1]).play();

				case "�s�u����":
					break;

				case "�����n�J":
					logErr_lb.setText(ms[1]);
					break;
				case "�i�J�n�J�e��":
					logErr_lb.setBounds(sett.getLogErr(true));
					logErr_lb.setHorizontalAlignment(JLabel.LEFT);
					c.add(usnm_lb, (Integer) 1);
					c.add(pswd_lb, (Integer) 1);
					c.add(usnm_ta, (Integer) 1);
					c.add(pswd_ta, (Integer) 1);
					c.add(login_btn, (Integer) 1);
					chat_pn = new ChatPane();
					logErr_lb.setText(null);
					break;
				case "�n�J���~":
					logErr_lb.setText(ms[1]);
					break;
				case "�i�J�j�U":
					Component[] cmps = c.getComponents();
					for (Component cmp : cmps) {
						if (cmp != $c && cmp != chat_pn)
							c.remove(cmp);
					}
					c.repaint();
					lobby = new Lobby();
					if (!c.isAncestorOf(chat_pn))
						c.add(chat_pn, (Integer) 1);
					break;
				case "�ж���T":
					// �ж���T:1�и�:2�ЦW:3���A:4�H��
					int r = Integer.parseInt(ms[1]);
					RoomLabel rm = lobby.rooms.get(r);
					rm.name_lb.setText(ms[2]);
					rm.setStatus(Integer.parseInt(ms[3]));
					rm.count_lb.setText(ms[4]);
					break;
				case "�i�J�ж�":
					// �i�J�ж�:�O�_������:�ж���:��
					room = new Room(Boolean.parseBoolean(ms[1]), Integer.parseInt(ms[2]));
					room.show();
					if (ms.length > 3)
						if (Boolean.parseBoolean(ms[3])) {
							bg_au = getAudio("�j�U");
							bg_au.play();
						}
					if (game != null) {
						game.chrs.clear();
						game.im_h_lb.clear();
						game.im_r_lb.clear();
						game.im_b_lb.clear();
						game.im_k_lb.clear();
						itl_lb = null;
						hcFld_lb = null;
						symFld_lb = null;
						timeline_lb = null;
						rec_btn = null;
						nRec_btn = null;
						ok_btn = null;
						ccl_btn = null;
						skp_btn = null;
						lev_btn = null;
						nLev_btn = null;
						ctn_btn = null;
						undo_btn = null;
						chooChr_pn = null;
						trash_lb = null;
						mt_lb = null;
						compS_lb = null;
						game = null;
					}
					break;

				case "�ж����H":
					// �ж����H:1��m:2���a�W��(-1�L�H -2����):3�O�_���Ǫ�:4�O�_�w�ǳ�
					boolean isBlocked = ms[2].equals("-2");
					String name = null;
					if (ms[2].equals("-1") || ms[2].equals("-2"))
						name = null;
					else
						name = ms[2];
					int p = Integer.parseInt(ms[1]);
					room.setPlayer(p, !isBlocked, name);
					if (Boolean.parseBoolean(ms[3]))
						room.setChief(p);
					else
						room.setReady(p, Boolean.parseBoolean(ms[4]));
					break;
				case "�����ܧ�":
					// �����ܧ�:�ڬO�_�O�s����:�s�����츹
					if (Boolean.parseBoolean(ms[1]))
						room.setChief(true);
					room.setChief(Integer.parseInt(ms[2]));
					break;
				case "���a�ǳ�":
					// ���a�ǳ�:1��m:2�O�_�ǳ�
					room.setReady(Integer.parseInt(ms[1]), Boolean.parseBoolean(ms[2]));
					break;
				case "sit":
					// sit:�ƭ�
					sit = ms[1];
					break;
				case "�C���}�l":
					// �C���}�l:�C���H��:�ڪ��y��
					if (true) { // ����n�ק�o��
						bg_au.stop();
						Component[] coms = c.getComponents();
						for (Component com : coms)
							if (com != $c)
								c.remove(com);
						c.repaint();

						game = new Game(Integer.parseInt(ms[1]), Integer.parseInt(ms[2]));
						gameInitializing(game.plyCount);
					}
					break;
				case "�﨤��P":
					// �﨤��P:����1:����2
					if (sit == "30") // 30�~���ġA�_�h����
						chooseChar(ms[1], ms[2]);
					break;
				case "�����T":
					game.iniSetChr(ms[1].split(","), ms[2].split(","));
					if (sit.equals("30")) {
						timeStop(); // ��ܨ����Ĳ�o
						c.remove(chooChr_pn);
						c.repaint();
					}
					break;
				case "�@����T":
					game.setChr(Integer.parseInt(ms[1]), ms[2]);
					break;
				case "�﨤����":
					if (sit.equals("30")) {
						timeStop(); // ��ܨ����Ĳ�o
						c.remove(chooChr_pn);
						c.repaint();
					}
				case "�A����T":
					int idy = Integer.parseInt(ms[2]);
					game.chrs.get(game.mySeat).readChr = ms[1];
					game.chrs.get(game.mySeat).idy = idy;
					game.chrs.get(game.mySeat).anmShowTeam(idy, false);
					break;
				case "�W�@��P":
					// �W�@��P:1�d�PID:2��s���P��(-1����)
					addHc(Integer.parseInt(ms[1]));
					if (!ms[2].equals("-1"))
						game.im_h_lb.get(game.mySeat).setText(ms[2]);
					break;
				case "�W�h��P":
					// �W�h��P:1�d�PID,�d�PID:2��s���P��(-1����)
					addHc(ms[1].split(","));
					if (!ms[2].equals("-1"))
						game.im_h_lb.get(game.mySeat).setText(ms[2]);
					break;
				case "�R�@��P":
					// �R�@��P:1�d�PID:2��s���P��(-1����)
					delHc(Integer.parseInt(ms[1]));
					if (!ms[2].equals("-1"))
						game.im_h_lb.get(game.mySeat).setText(ms[2]);
					break;
				case "�R�h��P":
					// �R�h��P:1�d�PID,�d�PID:2��s���P��(-1����)
					delHc(ms[1].split(","));
					if (!ms[2].equals("-1"))
						game.im_h_lb.get(game.mySeat).setText(ms[2]);
					break;
				case "��P�ʵe":
					// ��P�ʵe:1��:2�X�i:3��s���P�ƶq
					p = Integer.parseInt(ms[1]);
					if (p == -1) {
						bg_au.stop();
						bg_au = getAudio("�C����");
						bg_au.loop();
						// �Ҧ��H����P
						for (JLabel lb : game.im_h_lb)
							lb.setText(ms[3]);
						lev_btn.show(true, 4, "�D�зǰT��:�U��");
					} else
						anmDrawCard(p, Integer.parseInt(ms[2]), Integer.parseInt(ms[3]));
					break;
				case "���a�ʧ@":
					// ���a�ʧ@:1��:2�d�P�Χޯ�s��:3�b�Y�ӷ�:4�b�Y�ؼ�:5���a�Ѿl��P��(-1��ܤ���):6�n��
					int id = Integer.parseInt(ms[2]);
					int arr0 = Integer.parseInt(ms[3]);
					int arr1 = Integer.parseInt(ms[4]);
					game.funccards.add(new FunccardPanel(id, arr0, arr1));
					anmUseCard(Integer.parseInt(ms[1]), id, Integer.parseInt(ms[5]), arr0, arr1, ms[6]);
					if (id < 100)
						useCard_au.play();
					break;
				case "��@�쪱�a":
					// ��@�쪱�a:1�i�缾�a�츹,�i�缾�a�츹:2�O�_�j��:3������r:4����(�i�١ASetting.wt)(-1�A�����s�p��)
					isSelectingChr = true;
					ccl_btn.show(!Boolean.parseBoolean(ms[2]), 0, "��ܪ��a:-1");
					String[] pls = ms[1].split(",");
					ArrayList<String> strs = new ArrayList<>();
					strs.addAll(Arrays.asList(pls));
					for (int pl = 0; pl < game.plyCount; pl++)
						if (strs.contains(String.valueOf(pl)))
							game.chrs.get(pl).setClickable(1);
						else
							game.chrs.get(pl).setClickable(-1);
					compS_lb.setText(ms[3]);
					strs = null;
					if (ms.length > 4) {
						int ttt = Integer.parseInt(ms[4]);
						if (ttt != -1)
							timeStart(ttt, "��ܪ��a:-1");
					} else
						timeStart(Setting.wt, "��ܪ��a:-1");
					if (isSelectingHc != 1)
						skp_btn.message = "��ܪ��a:-1";
					break;
				case "�o�X����":
					// �o�X����:1�C���P�s��(97�K�q98���F��l�̽s��):2�ӷ����a�츹:3�ؼЪ��a�츹:4���(�ӷ����a�Ѿl��P��):5�r��(�n��)
					anmMoveItl(Integer.parseInt(ms[2]), Integer.parseInt(ms[3]), Integer.parseInt(ms[1]),
							Integer.parseInt(ms[4]), ms[5]);
					break;
				case "��������":
					// ��������:1�ؼЪ��a�츹
					anmMoveItl(Integer.parseInt(ms[1]));
					break;
				case "����ʧ@":
					timeStop();
					isSelectingHc = 0;
					isSelectingChr = false;
					isSelectingCard = false;
					isSelectingItl = 0;
					refreshHandcardLoc();
					refreshHandcardClickable();
					refreshCharacterClickable();
					clearFunccardClickable();
					ArrayList<SkillLabel> sks = new ArrayList<>();
					sks.addAll(skills.values());
					for (SkillLabel sk : sks)
						sk.setStatus(0);
					sks = null;
					ccl_btn.remove();// ���K�M�ū��s
					skp_btn.setClickable(false);
					ok_btn.remove();
					ctn_btn.remove();
					undo_btn.remove();
					rec_btn.remove();
					nRec_btn.remove();
					if (itl_pn != null) {
						c.remove(itl_pn);
						itl_pn = null;
					}
					if (see_pn != null) {
						c.remove(see_pn);
						see_pn = null;
					}
					c.repaint();
					sMax = -1;
					sMin = -1;
					list = null;
					selects = null;
					compS_lb.setText(null);
					break;
				case "�ۥѥX�P":
					// �ۥѥX�P:1���,���(�i�Τ�Pid):
					String[] $hcc = ms[1].split(","); // �i���P
					ArrayList<String> cardCanChosen = new ArrayList<>(Arrays.asList($hcc));
					isSelectingHc = 1;
					for (HandcardPanel hl : game.handcards)
						hl.setClickable(cardCanChosen.contains(String.valueOf(hl.id)));
					if (!time_th.isAlive())
						timeStart(Setting.wt, "�ϥΤ�P:-1");
					else
						time_th.setName("�ϥΤ�P:-1");
					skp_btn.show(true, 3, "�ϥΤ�P:-1");
					break;
				case "�{�ɭ���":
					isSelectingHc = 0;
					isSelectingChr = false;
					isSelectingCard = false;
					isSelectingItl = 0;
					refreshHandcardLoc();
					refreshHandcardClickable();
					refreshCharacterClickable();
					clearFunccardClickable();
					ccl_btn.remove();// ���K�M�ū��s
					skp_btn.setClickable(false);
					ok_btn.remove();
					ctn_btn.remove();
					undo_btn.remove();
					rec_btn.remove();
					nRec_btn.remove();
					if (itl_pn != null) {
						c.remove(itl_pn);
						itl_pn = null;
					}
					c.repaint();
					sMax = -1;
					sMin = -1;
					list = null;
					selects = null;
					break;
				case "��@�i��P":
					// ��@�i��P:1��Ƹs:2���L(�j��):3������r:4����(�i�١ASetting.wt)(-1�����s�p��)
					if (isSelectingHc == 1)
						isSelectingHc = 100;
					else {
						isSelectingHc = 2;
						skp_btn.show(true, 3, "��ܤ�P:-1");
					}
					ArrayList<String> hcc = new ArrayList<>(Arrays.asList(ms[1].split(",")));
					for (HandcardPanel hl : game.handcards)
						hl.setClickable(hcc.contains(String.valueOf(hl.id)));
					ccl_btn.show(!Boolean.parseBoolean(ms[2]), 0, "��ܤ�P:-1");
					compS_lb.setText(ms[3]);
					if (ms.length > 4) {
						int ttt = Integer.parseInt(ms[4]);
						if (ttt != -1)
							timeStart(ttt, "��ܤ�P:-1");
					} else
						timeStart(Setting.wt, "��ܤ�P:-1");
					break;
				case "������r":
					// ������r:��r | -1
					if (ms[1].equals("-1"))
						compS_lb.setText("");
					else
						compS_lb.setText(ms[1]);
					break;
				case "�O�_����":
					skp_btn.show(true, 3, "��������:false");
					rec_btn.show(true, 0, "��������:true");
					nRec_btn.show(true, 1, "��������:false");
					timeStart(Setting.wt, "��������:false");
					break;
				case "��������":
					// ��������:1���L(�O�_½�}):2���(�����s��):3���(���a�츹):4�r��(��m�A��r��|��b��|��k��):
					// 5���(�s������Ʀr):6�r��(����1��m):7�r��(����2��})(�i��)
					String sound1 = "-1", sound2 = "-1";
					if (ms.length > 6)
						sound1 = ms[6];
					if (ms.length > 7)
						sound2 = ms[7];
					anmRecItl(Integer.parseInt(ms[3]), Boolean.parseBoolean(ms[1]), Integer.parseInt(ms[2]), ms[4],
							Integer.parseInt(ms[5]), sound1, sound2);
					break;
				case "��@�i����":
					// ��@�i����:1��Ƹs:2���L(�O�_�j��):3������r:4���(����)(�i��)
					isSelectingItl = 1;
					if (isSelectingHc == 1)
						refreshCharacterClickable();
					else
						skp_btn.show(true, 3, "��ܱ���:-1");
					itl_pn = new ItlPanel("������", ms[1]);
					c.add(itl_pn, (Integer) 401);
					ccl_btn.show(!Boolean.parseBoolean(ms[2]), 0, "��ܱ���:-1");
					compS_lb.setText(ms[3]);
					if (ms.length > 4) {
						int ttt = Integer.parseInt(ms[4]);
						if (ttt != -1)
							timeStart(ttt, "��ܱ���:-1");
					} else
						timeStart(Setting.wt, "��ܱ���:-1");
					break;
				case "�^�ǰT��":
					// �^�ǰT��:�T��
					print("�T���^��:" + ms[1]);
					break;
				case "�d�P���f":
					// �d�P���f:1�s(�n�f)
					if (ms.length > 1) {
						String[] a_ns = ms[1].split(",");
						ArrayList<String> ns = new ArrayList<>(Arrays.asList(a_ns));
						int size = game.funccards.size();
						for (int i = 0; i < size; i++)
							game.funccards.get(i).setDark(ns.contains(String.valueOf(i)));
					}
					break;
				case "��@�i�d�P":
					// ��@�i�d�P:1�i�ѯ}��Ƹs:2�O�_�j��:3������r:4����(�i��)(-1����s)
					isSelectingCard = true;
					if (isSelectingHc == 1)
						refreshCharacterClickable();
					else
						skp_btn.show(true, 3, "��ܥd�P:-1");
					ccl_btn.show(!Boolean.parseBoolean(ms[2]), 0, "��ܥd�P:-1");
					String[] a_ns = ms[1].split(",");
					ArrayList<String> ns = new ArrayList<>(Arrays.asList(a_ns));
					int size = game.funccards.size();
					for (int i = 0; i < size; i++)
						game.funccards.get(i).setClickable(ns.contains(String.valueOf(i)));
					compS_lb.setText(ms[3]);
					if (ms.length > 4) {
						int ttt = Integer.parseInt(ms[4]);
						if (ttt != -1)
							timeStart(ttt, "��ܥd�P:-1");
					} else
						timeStart(Setting.wt, "��ܥd�P:-1");
					break;
				case "�}�l����":
					isQueuing = true;
					if (!fl.arws.isEmpty())
						fl.mouseExited(null);
					break;
				case "�����d�P":
					// �����d�P:�Ǹ�
					try {
						Thread.sleep(1000);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
					game.funccards.get(Integer.parseInt(ms[1])).setVisible(false);
					break;
				case "���a���A":
					// ���a���A:-1(����)
					p = Integer.parseInt(ms[1]);
					if (p == -1)
						for (LtdLabel ll : game.ltds)
							ll.setIcon(null);
					else
						anmLtd(p, Integer.parseInt(ms[2]), Boolean.parseBoolean(ms[3]));
					break;
				case "���⵲��":
					for (FunccardPanel fl : game.funccards)
						c.remove(fl);
					c.repaint();
					game.funccards.clear();
					isQueuing = false;
					break;
				case "�u���}�l":
					// �u���}�l:���a�ƶq
					lp = new LotteryPanel(Integer.parseInt(ms[1]));
					c.add(lp, (Integer) 299);
					break;
				case "�u������":
					c.remove(lp);
					c.repaint();
					lp = null;
					break;
				case "�u���o�P":
					// �u���o�P:1�d�Pid:2���a�츹:3��m:4��s��ƶq:5�n��1:6�n��2
					if (ms.length > 6)
						lp.getCard(Integer.parseInt(ms[2]), Integer.parseInt(ms[1]), ms[3], Integer.parseInt(ms[4]), ms[5],
								ms[6]);
					else
						lp.getCard(Integer.parseInt(ms[2]), Integer.parseInt(ms[1]), ms[3], Integer.parseInt(ms[4]), ms[5],
								"-1");
					break;
				case "�i��ձ�":
					// �i��ձ�:1�d�PID:2����:3�ʧ@:4���s�G
					test_pn = new TestPanel(Integer.parseInt(ms[1]), Integer.parseInt(ms[2]), Integer.parseInt(ms[3]),
							Integer.parseInt(ms[4]));
					test_pn.show();
					skp_btn.show(true, 3, "�^���ձ�:-1");
					timeStart(5000, "�^���ձ�:-1");
					break;
				case "�ձ�����":
					timeflag = false;
					c.remove(test_pn);
					c.repaint();
					test_pn = null;
					sl.isTesting = false;
					break;
				case "��@�P�ʵe":
					// ��@�i�P:1�d�Pid:2�ӷ����a�츹:3��m(rbk):4��s�᪺�Ʀr
					anmThrowCard(Integer.parseInt(ms[2]), Integer.parseInt(ms[1]), ms[3], Integer.parseInt(ms[4]));
					break;
				case "�}�B��r":
					floatText(ms[1]);
					break;
				case "�[�ݥd�P":
					// �[�ݥd�P:id:��r��
					see_pn = new SeeCardPanel(Integer.parseInt(ms[1]), ms[2]);
					see_pn.show();
					timeStart(Setting.wt, "�[�ݵ���");
					break;
				case "�[�ݵ���":
					timeflag = false;
					if (see_pn != null) {
						c.remove(see_pn);
						c.repaint();
						see_pn = null;
					}
					break;
				case "�[�ݥd�P��T":
					// �[�ݥd�P��T:1�d�P��:2���D
					if (seeItl_pn != null) {
						c.remove(seeItl_pn);
						c.repaint();
						seeItl_pn = null;
					}
					seeItl_pn = new SeeItlPanel(ms[2], ms[1]);
					c.add(seeItl_pn, (Integer) 403);
					break;
				case "���a�ӧQ":
					lev_btn.remove();
					nLev_btn.remove();
					c.repaint();
					// ���a�ӧQ:�ӧQ����:(�r��,���)�s
					int winTeam = Integer.parseInt(ms[1]);
					ArrayList<String> winners = new ArrayList<>();
					ArrayList<Integer> teams = new ArrayList<>();
					for (int i = 2; i < ms.length; i++) {
						winners.add(ms[i].split(",")[0]);
						teams.add(Integer.parseInt(ms[i].split(",")[1]));
					}
					showWinTeam(winTeam, winners, teams);
					break;
				case "���a���`":
					// ���a���`:1���a�츹:2����W��:3���`|��:4����
					if (Integer.parseInt(ms[1]) == game.mySeat) {
						lev_btn.remove();
						nLev_btn.remove();
						c.repaint();
					}
					game.chrs.get(Integer.parseInt(ms[1])).setStatus(Integer.parseInt(ms[3]), Integer.parseInt(ms[4]),
							ms[2]);
					break;
				case "½�}����":
					// XX����:�츹:����
					p = Integer.parseInt(ms[1]);
					getAudio("½�}����").play();
					game.chrs.get(p).open(true, ms[2]);
					game.chrs.get(p).chr = ms[2];
					break;
				case "�\�񨤦�":
					// XX����:�츹:����
					p = Integer.parseInt(ms[1]);
					game.chrs.get(p).open(false, ms[2]);
					game.chrs.get(p).chr = "����";
					break;
				case "�ʤ@�P�ʵe":
					// �ʤ@�P�ʵe:1�d�P�s��:2�ӷ����a�츹:3�ӭ��m:4�ӷ��s���:5�ؼЪ��a�츹:6�ؼЦ�m:7�ؼзs�Ʀr:8�n��1:9�n��2
					Move m0 = new Move(Integer.parseInt(ms[2]), ms[3], Integer.parseInt(ms[4]));
					Move m1 = new Move(Integer.parseInt(ms[5]), ms[6], Integer.parseInt(ms[7]));
					Moveable mov = new Moveable(m0, m1, Integer.parseInt(ms[1]), ms[8], ms[9]);
					moveCard(mov);
					m0 = null;
					m1 = null;
					mov = null;
					break;
				case "�ʦh�P�ʵe":
					String[] s1 = ms[1].split(",");
					String[] s2 = ms[2].split(","), s3 = ms[3].split(","), s4 = ms[4].split(","), s5 = ms[5].split(","),
							s6 = ms[6].split(","), s7 = ms[7].split(",");
					Moveable[] movs = new Moveable[s1.length];
					movs[0] = new Moveable(new Move(Integer.parseInt(s2[0]), s3[0], Integer.parseInt(s4[0])),
							new Move(Integer.parseInt(s5[0]), s6[0], Integer.parseInt(s7[0])), Integer.parseInt(s1[0]),
							ms[8], ms[9]);
					for (int i = 1; i < s1.length; i++) {
						movs[i] = new Moveable(new Move(Integer.parseInt(s2[i]), s3[i], Integer.parseInt(s4[i])),
								new Move(Integer.parseInt(s5[i]), s6[i], Integer.parseInt(s7[i])), Integer.parseInt(s1[i]),
								"-1", "-1");
					}
					moveCards(movs);
					s1 = null;
					s2 = null;
					s3 = null;
					s4 = null;
					s5 = null;
					s6 = null;
					s7 = null;
					movs = null;
					break;
				case "�פJ�ޯ�":
					// �פJ�ޯ�:id:id:id...
					int t = ms.length - 1;
					for (int i = 0; i < t; i++) {
						SkillLabel lb = new SkillLabel(Integer.parseInt(ms[i + 1]), i, t);
						skills.put(Integer.parseInt(ms[i + 1]), lb);
						c.add(lb, (Integer) 3);
					}
					break;
				case "�ޯ�]�w":
					// �ޯ�]�w:id:0,1,2:�ɶ�����(�ٲ��h���}�s�ɶ�)
					skills.get(Integer.parseInt(ms[1])).setStatus(Integer.parseInt(ms[2]));
					if (isSelectingHc != 1) {
						ccl_btn.show(true, 0, "�ϥΧޯ�:-1");
						ok_btn.show(true, 1, "�ϥΧޯ�:" + Integer.parseInt(ms[1]));
						skp_btn.show(true, 3, "�ϥΧޯ�:-1");
					}
					if (ms.length > 3) {
						if (Integer.parseInt(ms[3]) != -1)
							timeStart(Integer.parseInt(ms[3]), "�ϥΧޯ�:-1");
						else
							timeStart(Setting.wt, "�ϥΧޯ�:-1");
					}
					break;
				case "½�}����":
					// ½�}����:�츹:����:���ûP�_
					boolean show = true;
					if (ms.length > 3)
						show = Boolean.parseBoolean(ms[3]);
					game.chrs.get(Integer.parseInt(ms[1])).idy = Integer.parseInt(ms[2]);
					game.chrs.get(Integer.parseInt(ms[1])).anmShowTeam(Integer.parseInt(ms[2]), show);
					break;
				case "�ޯ�ʵe":
					// �ޯ�ʵe:����:�ޯ�W��:�n��:�b�Y1:�b�Y2
					SkillAnmPanel sk_pn = null;
					if (ms.length > 5)
						sk_pn = new SkillAnmPanel(ms[1], ms[2], Skill.getRed(ms[2]), ms[3], Integer.parseInt(ms[4]),
								Integer.parseInt(ms[5]));
					else
						sk_pn = new SkillAnmPanel(ms[1], ms[2], Skill.getRed(ms[2]), ms[3], -1, -1);
					sk_pn.start();
					sk_pn = null;
					break;
				case "��h�i��P":
					// ��h�i��P:1��Ƹs:2�j���:3�U��:4�W��:5����:6������r
					// (�j��h�U������0)
					selects = new ArrayList<>();
					list = new ArrayList<>();
					list.addAll(Arrays.asList(ms[1].split(",")));
					sMust = Boolean.parseBoolean(ms[2]);
					sMin = Integer.parseInt(ms[3]);
					sMax = Integer.parseInt(ms[4]);
					if (isSelectingHc != 1)
						skp_btn.show(true, 3, "��ܦh�i��P:-1");
					undo_btn.show(false, 2, null);
					ok_btn.show(false, 1, "!");
					if (isSelectingHc != 1)
						ccl_btn.show(!sMust, 0, "��ܦh�i��P:-1");
					for (HandcardPanel hl : game.handcards)
						hl.setClickable(list.contains(String.valueOf(hl.id)));
					isSelectingHc = 3;
					int time = Integer.parseInt(ms[5]);
					if (time != -1) {
						timeStart(time, "��ܦh�i��P:-1");
					}
					if (ms.length > 6)
						compS_lb.setText(ms[6]);
					break;
				case "��h�i����":
					// ��h�i��P:1��Ƹs:2�j���:3�U��:4�W��:5����:6������r
					// (�j��h�U������0)
					selects = new ArrayList<>();
					list = new ArrayList<>();
					list.addAll(Arrays.asList(ms[1].split(",")));
					sMust = Boolean.parseBoolean(ms[2]);
					sMin = Integer.parseInt(ms[3]);
					sMax = Integer.parseInt(ms[4]);
					if (isSelectingHc != 1)
						skp_btn.show(true, 3, "��ܦh�i����:-1");
					undo_btn.show(false, 2, null);
					ok_btn.show(false, 1, "!");
					ccl_btn.show(!sMust, 0, "��ܦh�i����:-1");
					itl_pn = new ItlPanel(ms[6], ms[1]);
					isSelectingItl = 2;
					c.add(itl_pn, (Integer) 401);
					time = Integer.parseInt(ms[5]);
					if (ms.length > 6)
						compS_lb.setText(ms[6]);
					if (time != -1)
						timeStart(Integer.parseInt(ms[5]), "��ܦh�i����:-1");
					break;
				case "�O�_�~��":
					// ���_�~��:1������r:2�ɶ�����
					ccl_btn.show(true, 0, "����~��:false");
					skp_btn.show(true, 3, "����~��:false");
					ctn_btn.show(true, 1, "����~��:true");
					timeStart(Integer.parseInt(ms[2]), "����~��:false");
					compS_lb.setText(ms[1]);
					break;
				case "���X���":
					// ���X���:������r:����
					ccl_btn.show(true, 0, "�ڪ����:false");
					skp_btn.show(true, 3, "�ڪ��~��:false");
					ok_btn.show(true, 1, "�ڪ����:true");
					timeStart(Setting.wt, "�ڪ����:false");
					compS_lb.setText(ms[1]);
					break;
				case "���a����":
					// ���a����:���a�ʺ�:���Ѥ��e
					StringBuffer sb = new StringBuffer();
					for (int i = 2; i < ms.length; i++) {
						sb.append(ms[i]);
						if (i != ms.length - 1)
							sb.append(":");
					}
					chat_pn.speak(ms[1], sb.toString());
					break;
				case "�t�λ���":
					sb = new StringBuffer();
					for (int i = 1; i < ms.length; i++) {
						sb.append(ms[i]);
						if (i != ms.length - 1)
							sb.append(":");
					}
					chat_pn.warn(sb.toString());
					break;
				case "���a�m�W":
					// ���a�m�W:name,name;
					String[] names = ms[1].split(",");
					for (int n = 0; n < names.length; n++)
						game.chrs.get(n).username_lb.setText(names[n]);
					break;
				case "���a�U��":
					// ���a�U��:���a�츹:��r;
					if (ms[2].equals("�����U��")) {
						game.chrs.get(Integer.parseInt(ms[1])).stus_lb.setText(null);
						if (Integer.parseInt(ms[1]) == game.mySeat) {
							nLev_btn.remove();
							lev_btn.show(true, 4, "�D�зǰT��:�U��");
						}
					} else if (ms[2].equals("�U��")) {
						game.chrs.get(Integer.parseInt(ms[1])).stus_lb.setText("�U��");
						if (Integer.parseInt(ms[1]) == game.mySeat) {
							lev_btn.remove();
							nLev_btn.show(true, 4, "�D�зǰT��:�����U��");
						}
					} else
						game.chrs.get(Integer.parseInt(ms[1])).stus_lb.setText(ms[2]);
					break;
				case "�`�λy":
					// �`�λy:�`�λy�r
					getAudio("�`�λy/" + ms[1]).play();
					break;
				case "�C������":
					game.chrs.get(Integer.parseInt(ms[1])).speak(ms[2]);
					break;
				default:
					new LogicException("�L�k�ѪR���T��: " + mess).printStackTrace();
					break;
				}
			}

		}
	}

	public void gameInitializing(int playerCount) {
		sit = "30";
		for (int i = 0; i < game.plyCount; i++) {
			game.chrs.add(new CharacterPanel(i));
			game.im_h_lb.add(new ImformLabel(i, "h"));
			game.im_r_lb.add(new ImformLabel(i, "r"));
			game.im_b_lb.add(new ImformLabel(i, "b"));
			game.im_k_lb.add(new ImformLabel(i, "k"));
			LtdLabel ltdlb = new LtdLabel();
			ltdlb.setLocation(sett.getChrX(i, game.plyCount), sett.getChrY(i, game.plyCount));
			game.ltds.add(ltdlb);
		}

		itl_lb = new IntelligenceLabel();

		hcFld_lb = new JLabel("", JLabel.CENTER);
		hcFld_lb.setBackground(Setting.onExited_col);
		hcFld_lb.setBounds(sett.getHcFld());
		hcFld_lb.setOpaque(true);
		hcFld_lb.setVisible(true);
		symFld_lb = new JLabel("", JLabel.CENTER);
		symFld_lb.setBackground(Setting.onExited_col);
		symFld_lb.setBounds(sett.getSymFld());
		symFld_lb.setOpaque(true);
		symFld_lb.setVisible(true);
		timeline_lb = new JLabel("", JLabel.CENTER);
		timeline_lb.setBounds(sett.getTimeline());
		timeline_lb.setVisible(true);
		timeline_lb.setOpaque(true);
		timeline_lb.setBackground(Color.YELLOW);

		rec_btn = new GameButton("����");
		nRec_btn = new GameButton("����");
		ok_btn = new GameButton("�T�{");
		ccl_btn = new GameButton("����");
		skp_btn = new GameButton("���L");
		lev_btn = new GameButton("�U��");
		nLev_btn = new GameButton("�ѰU");
		ctn_btn = new GameButton("�~��");
		undo_btn = new GameButton("����");

		chooChr_pn = new JPanel();
		chooChr_pn.setLayout(null);
		chooChr_pn.setOpaque(true);
		chooChr_pn.setVisible(true);
		chooChr_pn.setBackground(Color.GRAY);
		chooChr_pn.setBounds(sett.getChoChrFld());

		trash_lb = new TrashLabel("��P��");
		mt_lb = new TrashLabel("�P�w");

		compS_lb = new JLabel("", JLabel.CENTER);
		compS_lb.setVisible(true);
		compS_lb.setOpaque(false);
		compS_lb.setForeground(Color.YELLOW);
		compS_lb.setBounds(sett.getComp());
		compS_lb.setFont(sett.getCompFont());
		// ���﨤�ϳ��˦n�A�@�_���

		gamechat_ta = new IdealField();
		gamechat_ta.setBounds(sett.getGamechat());
		gamechat_ta.setVisible(true);
		gamechat_ta.setOpaque(false);
		gamechat_ta.addKeyListener(ckl);
		gamechat_ta.setBorder(BorderFactory.createEmptyBorder());
		gamechat_ta.setForeground(Color.WHITE);

		gamechatBtn_lb = new IdealLabel("�y��", "!�y��");
		gamechatBtn_lb.setBounds(sett.getGamechatBtn());

		gamechatSound_pn = new JPanel();
		gamechatSound_pn.setLayout(new GridLayout(12, 1));
		gamechatSound_pn.setVisible(true);
		gamechatSound_pn.setOpaque(true);
		gamechatSound_pn.add(new SpeakingLabel("�ڧ֥��O�ΤF"));
		gamechatSound_pn.add(new SpeakingLabel("���ͲH�w���n��"));
		gamechatSound_pn.add(new SpeakingLabel("�P��̳��X�ӧa"));
		gamechatSound_pn.add(new SpeakingLabel("���ͲH�w���n��"));
		gamechatSound_pn.add(new SpeakingLabel("�D�N���"));
		gamechatSound_pn.add(new SpeakingLabel("�S�Q�찵���ҳ��o�򬾷�"));
		gamechatSound_pn.add(new SpeakingLabel("���H�n�p�D���i�u���a"));
		gamechatSound_pn.add(new SpeakingLabel("���ҭ̳��M�~�I"));
		gamechatSound_pn.add(new SpeakingLabel("�Ǳi�Ū��椣��"));
		gamechatSound_pn.add(new SpeakingLabel("�������Ǳi����"));
		gamechatSound_pn.add(new SpeakingLabel("�d�@�ڤF"));
		gamechatSound_pn.add(new SpeakingLabel("�|�R��"));
		gamechatSound_pn.setSize(sett.getSpeak().width, sett.getSpeak().height * 12);
		gamechatSound_pn.setLocation(sett.getGamechatBtn().x + sett.getGamechatBtnWid() - gamechatSound_pn.getWidth(),
				sett.getGamechatBtn().y - gamechatSound_pn.getHeight());
	}

	public void chooseChar(String chr1, String chr2) {
		bg_au = getAudio("��ܨ���");
		bg_au.loop();
		sit = "30";
		JLabel chooChrBtn1_lb = new JLabel(chr1, JLabel.CENTER), chooChrBtn2_lb = new JLabel(chr2, JLabel.CENTER);
		JLabel chooChrPic1_lb = new JLabel((ImageIcon) in("character/" + chr1)),
				chooChrPic2_lb = new JLabel((ImageIcon) in("character/" + chr2));
		chooChrPic1_lb.setName(chr1);
		chooChrPic1_lb.addMouseListener(nl);
		chooChrPic1_lb.addMouseMotionListener(nml);
		chooChrPic2_lb.setName(chr2);
		chooChrPic2_lb.addMouseListener(nl);
		chooChrPic2_lb.addMouseMotionListener(nml);

		chooChrBtn1_lb.setOpaque(true);
		chooChrBtn1_lb.setVisible(true);
		chooChrBtn1_lb.setForeground(Color.WHITE);
		chooChrBtn1_lb.setBackground(Setting.onExited_col);
		chooChrBtn1_lb.setFont(sett.getChoChrFont());
		chooChrBtn1_lb.setBounds(sett.getChoChrBtn(1));
		chooChrBtn1_lb.addMouseListener(nl);
		chooChrBtn1_lb.addMouseMotionListener(nml);
		chooChrBtn1_lb.setName(chr1);

		chooChrBtn2_lb.setOpaque(true);
		chooChrBtn2_lb.setVisible(true);
		chooChrBtn2_lb.setForeground(Color.WHITE);
		chooChrBtn2_lb.setBackground(Setting.onExited_col);
		chooChrBtn2_lb.setFont(sett.getChoChrFont());
		chooChrBtn2_lb.setBounds(sett.getChoChrBtn(2));
		chooChrBtn2_lb.addMouseListener(nl);
		chooChrBtn2_lb.addMouseMotionListener(nml);
		chooChrBtn2_lb.setName(chr2);

		chooChrPic1_lb.setOpaque(true);
		chooChrPic1_lb.setVisible(true);
		chooChrPic1_lb.setBounds(sett.getChoChrPic(1));
		chooChrPic1_lb.setBackground(Setting.onExited_col);

		chooChrPic2_lb.setOpaque(true);
		chooChrPic2_lb.setVisible(true);
		chooChrPic2_lb.setBounds(sett.getChoChrPic(2));
		chooChrPic2_lb.setBackground(Setting.onExited_col);

		chooChr_pn.add(chooChrPic2_lb);
		chooChr_pn.add(chooChrPic1_lb);
		chooChr_pn.add(chooChrBtn2_lb);
		chooChr_pn.add(chooChrBtn1_lb);

		for (int i = 0; i < game.plyCount; i++) {
			game.chrs.get(i).show(1);
			c.add(game.im_h_lb.get(i), (Integer) 1);
			c.add(game.im_r_lb.get(i), (Integer) 1);
			c.add(game.im_b_lb.get(i), (Integer) 1);
			c.add(game.im_k_lb.get(i), (Integer) 1);
			c.add(game.ltds.get(i), (Integer) 2);
		}
		c.add(hcFld_lb, (Integer) 2);
		c.add(symFld_lb, (Integer) 2);
		c.add(timeline_lb, (Integer) 2);
		c.add(chooChr_pn, (Integer) 10);
		c.add(trash_lb, (Integer) 2);
		c.add(mt_lb, (Integer) 2);
		c.add(compS_lb, (Integer) 400);
		c.add(gamechat_ta, (Integer) 999);
		c.add(gamechatBtn_lb, (Integer) 999);
		timeStart(60000, "��ܨ���:-1");
	}

	// --------------------------------------------------------
	class Game {

		public ArrayList<HandcardPanel> handcards = new ArrayList<>(); // ��m��Pid
		public ArrayList<FunccardPanel> funccards = new ArrayList<>(); // ��ݵ���\��Pid
		public ArrayList<CharacterPanel> chrs = new ArrayList<>();
		public ArrayList<LtdLabel> ltds = new ArrayList<>();
		public ArrayList<ImformLabel> im_h_lb = new ArrayList<>(), im_r_lb = new ArrayList<>(), im_b_lb = new ArrayList<>(),
				im_k_lb = new ArrayList<>(); // ����ϡB��P�ϡB���Ŷ±�����T��
		public int plyCount;
		public int myIdy = -1;
		private int mySeat;
		public boolean isMyChrCov;
		public boolean isMyIdyCov = true;
		public String myChr;
		public int myStatus = 1; // 1�b�u2²��U��3���U��

		public Game(int total, int s) {
			plyCount = total;
			setMySeat(s);
		}

		public void setMySeat(int s) {
			mySeat = s;
			sett.seat = mySeat;
		}

		public int getMySeat() {
			return mySeat;
		}

		public void setChr(int p, String c) {
			chrs.get(p).setChr(c);
		}

		public void iniSetChr(String[] pls, String[] chs) {
			for (int i = 0; i < pls.length; i++)
				chrs.get(Integer.parseInt(pls[i])).setChr(chs[i]);
		}
	}

	public void addHc(int id) {
		game.handcards.add(new HandcardPanel(id));
		int t = game.handcards.size();
		for (int i = 0; i < t; i++)
			game.handcards.get(i).setLocation(sett.getHandcardX(i, t, -1), sett.getHandcardY());
		game.handcards.get(t - 1).show(t - 1 + 10);
	}

	public void addHc(String[] ids) {
		int t0 = game.handcards.size();
		for (String id : ids)
			game.handcards.add(new HandcardPanel(Integer.parseInt(id)));
		int t1 = game.handcards.size();
		for (int i = 0; i < t1; i++)
			game.handcards.get(i).setLocation(sett.getHandcardX(i, t1, -1), sett.getHandcardY());
		for (int i = t0; i < t1; i++)
			game.handcards.get(i).show(i + 10);
	}

	public void delHc(int id) {
		HandcardPanel dl = null;
		for (HandcardPanel lb : game.handcards)
			if (lb.id == id) {
				dl = lb;
				break;
			}
		if (dl == null)
			new LogicException("�Q�n�R���@�i�s���� " + id + " ���P�A�� game.hc �L���P").printStackTrace();
		else {
			game.handcards.remove(dl);
			int t = game.handcards.size();
			c.remove(dl);
			c.repaint();
			for (int i = 0; i < t; i++) {
				HandcardPanel hl = game.handcards.get(i);
				hl.setLocation(sett.getHandcardX(i, t, -1), sett.getHandcardY());
				c.add(hl, (Integer) (10 + i));
			}
		}
	}

	public void delHc(String[] ids) {
		for (String id : ids) {
			Integer iid = Integer.parseInt(id);
			HandcardPanel dl = null;
			for (HandcardPanel lb : game.handcards)
				if (lb.id == iid) {
					dl = lb;
					break;
				}
			if (dl == null)
				new LogicException("�Q�n�R���@�i�s���� " + id + " ���P�A�� game.hc �L���P").printStackTrace();
			game.handcards.remove(dl);
			c.remove(dl);
		}
		int t = game.handcards.size();
		c.repaint();
		for (int i = 0; i < t; i++) {
			game.handcards.get(i).setLocation(sett.getHandcardX(i, t, -1), sett.getHandcardY());
			c.add(game.handcards.get(i), (Integer) (10 + i));
		}
	}

	public void anmEvyDrawCard(int t, int cc) {
		// num�C�ӤH��F�X�i�P
	}

	@SuppressWarnings("unchecked")
	public void anmDrawCard(int p, int t, int cc) {
		// t��F�X�i�P
		// cc��s�᪺��P�ƶq�A-1��ܤ���

		JLabel[] lbs = new JLabel[t];
		// ���ʪ��P
		ArrayList<ArrayList<Rectangle>> rec = new ArrayList<>();
		// �̭���ArrayList��C�i�P�C�@�誺rectangle�A�~������ܭ��i�P

		int[] x0 = new int[t];
		int y0 = sett.getAnmDrawCardY(), y1 = sett.getImY(p, game.plyCount), w0 = sett.getFccWid(), h0 = sett.getFccHei(),
				h1 = sett.getImHei(), w1 = Math.round((float) w0 * h1 / h0),
				x1 = sett.getImX(p, game.plyCount, "h") + ((sett.getImWid(game.mySeat, game.plyCount, "h") - w1) / 2);

		float[] dx = new float[t];
		float dy = (float) (y1 - y0) / (Setting.anmDrawCardTime / Setting.anmSpc),
				dw = (float) (w1 - w0) / (Setting.anmDrawCardTime / Setting.anmSpc),
				dh = (float) (h1 - h0) / (Setting.anmDrawCardTime / Setting.anmSpc); // �C��x�My�Ȫ��ܤƶq
		for (int i = 0; i < t; i++) {
			x0[i] = sett.getAnmDrawCardX(i, t);
			dx[i] = (float) (x1 - x0[i]) / (Setting.anmDrawCardTime / Setting.anmSpc);
			rec.add(new ArrayList<Rectangle>());
			lbs[i] = new JLabel((ImageIcon) in("gamecard/0"));
			lbs[i].setVisible(true);
			lbs[i].setOpaque(false);

		}
		for (int j = 0; j <= Setting.anmDrawCardTime / Setting.anmSpc; j++) {
			int yy = Math.round(y0 + dy * j); // �]�C��rec��y�ҬۦP�A�������誺y�Ȫ�����J�Ҧ�rec
			int ww = Math.round(w0 + dw * j);
			int hh = Math.round(h0 + dh * j);
			for (int i = 0; i < t; i++)
				rec.get(i).add(new Rectangle(Math.round(x0[i] + dx[i] * j), yy, ww, hh));
		}
		HashMap<Integer, ImageIcon> bgCards = (HashMap<Integer, ImageIcon>) in("gamecard/card_size0");

		// ---------------------------
		// �ʵe�}�l
		for (int i = 0; i < t; i++)
			lbs[i].setBounds(rec.get(i).get(0));
		for (int i = 0; i < t; i++)
			c.add(lbs[i], (Integer) (300 + i));
		drawCard_au.play();
		try {
			Thread.sleep(Setting.anmDrawCardShowTime);
		} catch (InterruptedException e1) {
			e1.printStackTrace();
		}

		for (int j = 0; j <= Setting.anmDrawCardTime / Setting.anmSpc; j++) {
			for (int i = 0; i < t; i++) {
				lbs[i].setBounds(rec.get(i).get(j));
				lbs[i].setIcon(bgCards.get(rec.get(i).get(j).height));
			}
			try {
				Thread.sleep(Setting.anmSpc);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		// �ʵe����
		// ---------------�{�{
		for (JLabel lb : lbs)
			c.remove(lb);
		c.repaint();
		shine(game.im_h_lb.get(p), cc, game.im_h_lb.get(p).getBackground());
		// ---------------
	}

	public void anmUseCard(int p, int id, int cc, int arr0, int arr1, String sound) {
		// p �֥ΤF�P
		// id �Ϊ��P���s��
		// cc �L�Ѿl��P��
		AudioClip au = null;
		if (!sound.equals("-1"))
			au = getAudio(sound);
		int n = game.funccards.size();
		int x0 = sett.getIniFunccard(p, game.plyCount).x, y0 = sett.getIniFunccard(p, game.plyCount).y,
				x1 = sett.getFccX(n - 1, n, -1), y1 = sett.getFccY();
		int js = Setting.anmUseCardTime / Setting.anmSpc; // �e��ơA�Ĥ@�欰0
		float dx = (float) (x1 - x0) / js, dy = (float) (y1 - y0) / js;
		FunccardPanel lb = game.funccards.get(n - 1);
		lb.setLocation(x0, y0);
		if (cc != -1)
			game.im_h_lb.get(p).setText(String.valueOf(cc));
		lb.show((Integer) (100 + n - 1));

		// �b�y(�}�s������P�ɶi��)
		if (arr0 != -1 && arr1 != -1 && arr0 < 10 && arr1 < 10)
			drawArrow(arr0, arr1);

		// ����
		for (int a = 0; a <= js; a++) {
			lb.setLocation(Math.round(x0 + dx * a), Math.round(y0 + dy * a));
			try {
				Thread.sleep(Setting.anmSpc);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}

		// ���s��z�P����m
		refreshFunccardLoc();

		// �����n��
		if (au != null) {
			au.play();
		}
	}

	public void anmMoveItl(int s, int f, int id, int cc, String sound) {
		// �o�e�����ɩI�s����k�޵o�ʵe
		// target�������ʨ����(sf�l�ת��a�츹)
		// sound ���Ħ�}
		final int sx = sett.getItlX(s, game.plyCount);
		final int sy = sett.getItlY(s, game.plyCount);
		final int fx = sett.getItlX(f, game.plyCount);
		final int fy = sett.getItlY(f, game.plyCount);
		final int js /* �e��ơA�Ĥ@�欰0 */ = Setting.itlAnmTime / Setting.anmSpc;
		final float dx = (float) (fx - sx) / js;
		final float dy = (float) (fy - sy) / js;
		itl_lb.setId(id);
		game.im_h_lb.get(s).setText(String.valueOf(cc));
		itl_lb.setBounds(sx, sy, sett.getItlWid(), sett.getItlHei());
		if (!sound.equals("-1"))
			getAudio(sound).play();
		c.add(itl_lb, (Integer) 3);
		try {
			Thread.sleep(450);
		} catch (InterruptedException e1) {
			e1.printStackTrace();
		}
		for (int a = 1; a <= js; a++) {
			itl_lb.setLocation(Math.round(sx + dx * a), Math.round(sy + dy * a));
			try {
				Thread.sleep(Setting.anmSpc);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}

	}

	public void anmMoveItl(int f) {
		// �������ʩI�s����k�A�o�����ɤ��n�Φ���k
		final int sx = itl_lb.getX();
		final int sy = itl_lb.getY();
		final int fx = sett.getItlX(f, game.plyCount);
		final int fy = sett.getItlY(f, game.plyCount);
		final int js /* �e��ơA�Ĥ@�欰0 */ = Setting.itlAnmTime / Setting.anmSpc;
		final float dx = (float) (fx - sx) / js;
		final float dy = (float) (fy - sy) / js;
		for (int a = 0; a <= js; a++) {
			itl_lb.setLocation(Math.round(sx + dx * a), Math.round(sy + dy * a));
			try {
				Thread.sleep(Setting.anmSpc);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}

	@SuppressWarnings("unchecked")
	public void anmRecItl(int who, boolean open, int id, String loc, int cc, String sound1, String sound2) {

		final int sx = itl_lb.getX();
		final int sy = itl_lb.getY();
		final int mx = sx - (sett.getFccWid() - sett.getItlWid()) / 2;
		final int my = sy - (sett.getFccHei() - sett.getItlHei()) / 2;
		final int fh = sett.getImHei();
		final int fw = Math.round((float) fh * sett.getFccHei() / sett.getFccWid());
		final int fx = sett.getImX(who, game.plyCount, loc) + (sett.getImWid(who, game.plyCount, loc) - fw) / 2;
		final int sw = sett.getItlWid();
		final int sh = sett.getItlHei();
		final int mw = sett.getFccWid();
		final int mh = sett.getFccHei();
		final int js = Setting.anmUpTime / Setting.anmSpc;
		final float dw = (float) (mw - sw) / js;
		final float dh = (float) (mh - sh) / js;
		final int fy = sett.getImY(who, game.plyCount);
		final int ks = Setting.anmDownTime / Setting.anmSpc;
		final float dx = (float) (mx - sx) / js;
		final float dy = (float) (my - sy) / js;
		final float ex = (float) (fx - mx) / ks;
		final float ey = (float) (fy - my) / ks;

		final float ew = (float) (fw - mw) / ks;
		final float eh = (float) (fh - mh) / ks;

		final int fileId = Tool.toFileId(id);

		HashMap<Integer, ImageIcon> img1 = (HashMap<Integer, ImageIcon>) in("gamecard/card_size" + fileId);

		JLabel lb = null;
		switch (loc) {
		case "r":
			lb = game.im_r_lb.get(who);
			break;
		case "b":
			lb = game.im_b_lb.get(who);
			break;
		case "k":
			lb = game.im_k_lb.get(who);
			break;
		default:
			new LogicException("���ӥX�{���C��: " + loc).printStackTrace();
			return;
		}

		AudioClip au1 = null, au2 = null;
		if (!sound1.equals("-1"))
			au1 = getAudio(sound1);
		if (!sound2.equals("-1"))
			au2 = getAudio(sound2);

		if (open) {
			ArrayList<ImageIcon> img = null;// = ci.openCard(fileId, itl_lb.id,
											// true);
			if (itl_lb.id == 97)
				img = (ArrayList<ImageIcon>) in("gamecard/getItlBy97" + fileId);
			else if (itl_lb.id == 98)
				img = (ArrayList<ImageIcon>) in("gamecard/getItlBy98" + fileId);
			else
				new LogicException("�������J�����K�q�S�������F: " + itl_lb.id);
			for (int j = 0; j <= js; j++) {
				itl_lb.setBounds(Math.round(sx + dx * j), Math.round(sy + dy * j), Math.round(sw + dw * j),
						Math.round(sh + dh * j));
				itl_lb.setIcon(img.get(j));
				try {
					Thread.sleep(Setting.anmSpc);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}

		} else { // ����½�}�ʧ@

			HashMap<Integer, ImageIcon> img = (HashMap<Integer, ImageIcon>) in("gamecard/card_size" + fileId);// ci.getCards_size(fileId);

			for (int j = 0; j <= js; j++) {
				int w = Math.round(sw + dw * j);
				int h = Math.round(sh + dh * j);
				itl_lb.setBounds(Math.round(sx + dx * j), Math.round(sy + dy * j), w, h);
				itl_lb.setIcon(img.get(h));
				try {
					Thread.sleep(Setting.anmSpc);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}

		// �����Ȱ�
		try {
			Thread.sleep(Setting.anmUpAndDownPauseTime);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

		// �M��O�U�h���ʵe

		for (int k = 1; k <= ks; k++) {
			int w = Math.round(mw + ew * k);
			int h = Math.round(mh + eh * k);
			itl_lb.setBounds(Math.round(mx + ex * k), Math.round(my + ey * k), w, h);
			itl_lb.setIcon(img1.get(h));
			try {
				Thread.sleep(Setting.anmSpc);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		if (au1 != null)
			au1.play();
		if (au2 != null)
			au2.play();

		c.remove(itl_lb);
		c.repaint();
		itl_lb.setId(-1);
		shine(lb, cc, lb.getBackground());
	}

	@SuppressWarnings("unchecked")
	public void anmThrowCard(int who, int id, String loc, int cc) {

		final int sh = sett.getImHei();
		final int mh = sett.getFccHei();
		final int mw = sett.getFccWid();
		final int sw = Math.round((float) (sh * mw) / mh);
		final int fw = sett.getTrashLng() / 2;
		final int fh = Math.round((float) (mh * fw) / mw);

		final int sx = sett.getImX(who, game.plyCount, loc);
		final int sy = sett.getImY(who, game.plyCount);
		final int mx = (sett.scnWidth - sett.getFccWid()) / 2;
		final int my = (sett.scnHeight - sett.getFccHei()) / 2;
		final int fx = sett.getTrash().x + (sett.getTrashLng() - fw) / 2;
		final int fy = sett.getTrash().y + (sett.getTrashLng() - fh) / 2;

		HashMap<Integer, ImageIcon> imgs = (HashMap<Integer, ImageIcon>) in("gamecard/card_size" + Tool.toFileId(id));// ci.getCards_size(Tool.toFileId(id));
		int us = Setting.anmThrowUpTime / Setting.anmSpc, ds = Setting.anmThrowDownTime / Setting.anmSpc;

		final float dx = (float) (mx - sx) / us;
		final float dy = (float) (my - sy) / us;
		final float dw = (float) (mw - sw) / us;
		final float dh = (float) (mh - sh) / us;
		final float ex = (float) (fx - mx) / ds;
		final float ey = (float) (fy - my) / ds;
		final float ew = (float) (fw - mw) / ds;
		final float eh = (float) (fh - mh) / ds;

		JLabel imglb = new JLabel(imgs.get(sh), JLabel.CENTER);
		JLabel shine1 = null;
		switch (loc) {
		case "r":
			shine1 = game.im_r_lb.get(who);
			break;
		case "b":
			shine1 = game.im_b_lb.get(who);
			break;
		case "k":
			shine1 = game.im_k_lb.get(who);
			break;
		case "h":
			shine1 = game.im_h_lb.get(who);
			break;
		default:
			new LogicException("�����T�� loc = " + loc).printStackTrace();
			break;
		}
		imglb.setVisible(true);
		imglb.setBounds(sx, sy, sw, sh);
		imglb.setOpaque(false);
		c.add(imglb, (Integer) 300);
		shine(shine1, cc, shine1.getBackground());

		for (int u = 0; u <= us; u++) {
			int h = Math.round(sh + dh * u);
			imglb.setBounds(Math.round(sx + dx * u), Math.round(sy + dy * u), Math.round(sw + dw * u), h);
			imglb.setIcon(imgs.get(h));
			try {
				Thread.sleep(Setting.anmSpc);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}

		try {
			Thread.sleep(Setting.anmThrowPauseTime);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

		for (int d = 0; d <= ds; d++) {
			int h = Math.round(mh + eh * d);
			imglb.setBounds(Math.round(mx + ex * d), Math.round(my + ey * d), Math.round(mw + ew * d), h);
			imglb.setIcon(imgs.get(h));
			try {
				Thread.sleep(Setting.anmSpc);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		c.remove(imglb);
		c.repaint();
		shine(trash_lb, -1, trash_lb.getBackground());

	}

	public void anmThrowCards() {

	}

	@SuppressWarnings("unchecked")
	public void anmLtd(int who, int ltd, boolean cover) {
		ArrayList<ImageIcon> img = null;
		switch (ltd) {
		case 1: // l
			img = (ArrayList<ImageIcon>) in("ltd/l");
			break;
		case 2: // d
			img = (ArrayList<ImageIcon>) in("ltd/d");
			break;
		case 3: // t;
			img = (ArrayList<ImageIcon>) in("ltd/t");
			break;
		default:
			new LogicException("���~��ltd��: " + ltd);
			return;
		}

		LtdLabel ltdlb = game.ltds.get(who);
		JLabel lb = new JLabel();
		lb.setVisible(true);
		lb.setOpaque(false);
		lb.setBounds(ltdlb.getBounds());
		c.add(lb, (Integer) 3);
		for (ImageIcon ii : img) {
			lb.setIcon(ii);
			try {
				Thread.sleep(Setting.anmSpc);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		if (cover) {
			ltdlb.setIcon(lb.getIcon());
			ltdlb.ltd = ltd;
		}
		c.remove(lb);
		c.repaint();
	}

	public void seeCard(int id, String comp) {

	}

	public void refreshLtd() {
		for (LtdLabel ll : game.ltds) {
			ll.setIcon(null);
			ll.ltd = 0;
		}
	}

	public void shine(JLabel lb, int cc, Color col) {
		if (col != null) {
			Runnable shine_run = () -> {
				ArrayList<Color> cols = sett.shine(col);
				for (Color c : cols) {
					lb.setBackground(c);
					try {
						Thread.sleep(Setting.anmSpc);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
				// ---------------
				if (cc != -1)
					lb.setText(String.valueOf(cc));
				// ---------------
				for (int bg = cols.size() - 2; bg >= 0; bg--) {
					lb.setBackground(cols.get(bg));
					try {
						Thread.sleep(Setting.anmSpc);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
			};
			new Thread(shine_run).start();
		}
	}

	class ArrowLabel extends JLabel { // �쥻�O�~��JPanel���C�A�`�N�@�U

		public static final double turn = Math.PI / 6; // (���ਤ30��)
		public static final int speed = 3; // (ms/�ӽb�Y);
		public static final double spc = 18; // ��l�p�b�Y���������Z
		public static final int pauseTime = 600; // ��ܧ��b�y��b�y��������������ɶ�(�@��)
		public static final int border = 3; // �b�Y�ʫ�
		public static final int length = 18; // �p�b������(�ҥH��panel�ܤ֪��e�nlength
												// *2�A�]���_�l�I�b����)
		int x0, y0; // �p�b�����ߦ�m�A���ӭn�Olength�~��
		int dx1, dx2, dy1, dy2; // �첾

		// �`�N�A��setLoction��setBounds�ɡA�Nx�Py�]�����ߦ�m�Y�i�A�w�g�мg�ﵽ�F�I

		public ArrowLabel() {

		}

		public ArrowLabel(int $dx, int $dy) {
			// $x, $y��ܦV�q(�_�I-���I�~��!!)
			double $lng = Math.sqrt($dx * $dx + $dy * $dy); // ���V�q����
			double r = (double) length * sett.rate / $lng; // ���v
			x0 = (int) (length * sett.rate);
			y0 = (int) (length * sett.rate);
			dx1 = (int) ((Math.cos(turn) * $dx - Math.sin(turn) * $dy) * r);
			dy1 = (int) ((Math.sin(turn) * $dx + Math.cos(turn) * $dy) * r);
			dx2 = (int) ((Math.cos(-turn) * $dx - Math.sin(-turn) * $dy) * r);
			dy2 = (int) ((Math.sin(-turn) * $dx + Math.cos(-turn) * $dy) * r);
			setVisible(true);
			setOpaque(false);
			setSize(getPanelLng(), getPanelLng());
		}

		@Override
		public void paint(Graphics g) {
			((Graphics2D) g).setStroke(new BasicStroke((float) (border * sett.rate))); // �u���ʫ�
			((Graphics2D) g).setColor(Color.ORANGE); // �u���C��
			((Graphics2D) g).drawLine(x0, y0, x0 + dx1, y0 + dy1); // �e�Ĥ@���u
			((Graphics2D) g).drawLine(x0, y0, x0 + dx2, y0 + dy2); // �e�Ĥ@���u
		}

		public int getPanelLng() {
			return (int) (length * 2 * sett.rate);
		}

		@Override
		public void setBounds(int x, int y, int w, int h) {
			super.setBounds(x - getPanelLng() / 2, y - getPanelLng() / 2, w, h);
		}

		public double getSpace() {
			return spc * sett.rate;
		}
	}

	public void drawArrow(int arr0, int arr1) { // arr0�Marr1��ܰ_���I�N�X

		Runnable r = () -> {
			int x0, x1, y0, y1; // �_���I���ߦ�m~!
			if (arr0 >= 0 && arr0 < 10) { // �o�O���a�츹
				x0 = sett.getChrX(arr0, game.plyCount) + (sett.getChrWid()) / 2;
				y0 = sett.getChrY(arr0, game.plyCount) + (sett.getChrHei()) / 2;
			} else {
				new AnimationException("�b�Yø�ϮɥX�{���~�N�X arr0 = " + arr0).printStackTrace();
				return;
			}
			if (arr1 >= 0 && arr1 < 10) { // �o�O���a�츹
				x1 = sett.getChrX(arr1, game.plyCount) + (sett.getChrWid()) / 2;
				y1 = sett.getChrY(arr1, game.plyCount) + (sett.getChrHei()) / 2;
			} else if (arr1 >= 200 && arr1 < 300) { // �ݵ���d�P
				x1 = sett.getFccX(arr1 - 200, game.funccards.size(), arr1 - 200) + sett.getFccWid() / 2; // ���ɳo�i�d�P�n�Q�E�J!!
				y1 = sett.getFccY() + sett.getFccHei() / 2;
			} else if (arr1 == 300) {// ����
				x1 = itl_lb.getX() + sett.getItlWid() / 2;
				y1 = itl_lb.getY() + sett.getItlHei() / 2;
			} else {
				new AnimationException("�b�Yø�ϮɥX�{���~�N�X arr1 = " + arr1).printStackTrace();
				return;
			}

			int vx = x0 - x1, vy = y0 - y1; // �p�b���ݭn���V�q(�_�I����I)
			double l = Math.sqrt(vx * vx + vy * vy); // �`�V�q����
			int t = Math.round((float) (l / new ArrowLabel().getSpace())); // �|���X�Ӥp�b��
			double s = l / t; // �վ�L��b��������
			float dx, dy;
			if (x1 == x0)
				dx = 0;
			else
				dx = (float) (s * (x1 - x0) / l);
			if (y1 == y0)
				dy = 0;
			else
				dy = (float) (s * (y1 - y0) / l); // �b�y�V�q����
			ArrayList<ArrowLabel> arws = new ArrayList<>();
			for (int ar = 0; ar <= t; ar++) {
				ArrowLabel al = new ArrowLabel(vx, vy);
				al.setLocation(Math.round(x0 + dx * ar), Math.round(y0 + dy * ar));
				arws.add(al);
			}

			for (ArrowLabel al : arws) {
				try {
					c.add(al, (Integer) (400));
					Thread.sleep(ArrowLabel.speed);
				} catch (InterruptedException e) {
					e.printStackTrace();
				} catch (ArrayIndexOutOfBoundsException e) {
					e.printStackTrace();
				}
			}
			try {
				Thread.sleep(ArrowLabel.pauseTime);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			for (ArrowLabel al : arws) {
				try {
					c.remove(al);
					c.repaint();
					Thread.sleep(ArrowLabel.speed);
				} catch (InterruptedException e) {
					e.printStackTrace();
				} catch (ArrayIndexOutOfBoundsException e) {
					e.printStackTrace();
				}
			}
		};

		new Thread(r).start();
	}

	// ---------------------------------------------------------
	public void refreshFunccardLoc() {
		int t = game.funccards.size();
		for (int i = 0; i < t; i++)
			game.funccards.get(i).setLocation(sett.getFccX(i, t, -1), sett.getFccY());
	}

	public void refreshHandcardLoc() {
		int t = game.handcards.size();
		for (int i = 0; i < t; i++) {
			game.handcards.get(i).setLocation(sett.getHandcardX(i, t, -1), sett.getHandcardY());
			game.handcards.get(i).setVisible(true);
		}
	}

	public void refreshCharacterClickable() {
		isSelectingChr = false;
		for (CharacterPanel cl : game.chrs)
			cl.setClickable(0);
	}

	public void refreshHandcardClickable() {
		isSelectingHc = 0;
		hl.selected = -1;
		for (HandcardPanel hl : game.handcards)
			hl.setClickable(false);
	}

	public void timeStart(int time, String mess) {
		ttt = time;
		if (time_th.isAlive()) {
			timeflag = false;
		}
		try {
			time_th.join();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		time_th = new Thread(time_run, mess);
		time_th.start();
	}

	public void clearFunccardClickable() {
		for (FunccardPanel fl : game.funccards) {
			fl.clickable = false;
			fl.setDark(fl.dark);
		}
	}

	public void timeStop() {
		timeflag = false;
	}

	public void floatText(String t) {
		JLabel lb = new JLabel(t, JLabel.CENTER);
		lb.setFont(sett.getFloatCompFont());
		lb.setForeground(Color.YELLOW);
		lb.setVisible(true);
		lb.setOpaque(false);
		lb.setBounds(sett.getFloatCompX(), sett.getFloatCompY() + sett.getFloatCompHei(), sett.getCompWid(), 1);

		final int sy = sett.getFloatCompY() + sett.getFloatCompHei();
		final int my = sett.getFloatCompY();
		final int fy = sett.getFloatCompFY();
		final int js0 = Setting.floatTxtShow / Setting.anmSpc;
		final int js1 = Setting.floatTxtDis / Setting.anmSpc;
		final float dy0 = (float) (my - sy) / js0;
		final float dy1 = (float) (fy - my) / js1;

		Runnable float_run = () -> {
			floatNum++;
			c.add(lb, (Integer) 400);
			for (int i = 0; i <= js0; i++) {
				lb.setBounds(sett.getFloatCompX(), Math.round(sy + dy0 * i), sett.getCompWid(),
						Math.round((float) sett.getFloatCompHei() * i / js0));
				try {
					Thread.sleep(Setting.anmSpc);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
			int sleep = 0;
			while (sleep <= Setting.floatTxtPause && floatNum == 1) {
				try {
					Thread.sleep(Setting.anmSpc);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				sleep += Setting.anmSpc;
			}
			floatNum--;
			for (int i = 0; i <= js1; i++) {
				lb.setLocation(sett.getFloatCompX(), Math.round(my + dy1 * i));
				lb.setForeground(new Color(Color.YELLOW.getRed(), Color.YELLOW.getGreen(), Color.YELLOW.getBlue(),
						255 - Math.round(255.0f * i / js1)));
				try {
					Thread.sleep(Setting.anmSpc);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
			c.remove(lb);
			c.repaint();
			floatNum--;
		};
		new Thread(float_run, "�}�B��r").start();
	}

	public void showWinTeam(int winTeam, ArrayList<String> winners, ArrayList<Integer> teams) {
		win_lb = new ArrayList<>();
		int bw = (int) (10 * sett.rate);
		int size = winners.size();
		for (int i = 0; i < size; i++) {
			JLabel lb = null;
			if (size == 1)
				lb = new JLabel((ImageIcon) in("character/chrWin" + winners.get(i)));
			else
				lb = new JLabel((ImageIcon) in("character/chrsWin" + winners.get(i)));
			lb.setOpaque(false);
			lb.setVisible(true);
			lb.setBounds(sett.getWinChr(i, size));
			switch (teams.get(i)) {
			case 1:
				lb.setBorder(BorderFactory.createMatteBorder(0, 0, bw, 0, Setting.team1_col));
				break;
			case 2:
				lb.setBorder(BorderFactory.createMatteBorder(0, 0, bw, 0, Setting.team2_col));
				break;
			case 3:
				lb.setBorder(BorderFactory.createMatteBorder(0, 0, bw, 0, Setting.team3_col));
				break;
			}
			win_lb.add(lb);
		}
		winTeam_lb = new JLabel((ImageIcon) in("team/win" + (winTeam)));
		winTeam_lb.setVisible(true);
		winTeam_lb.setOpaque(false);
		winTeam_lb.setBounds(sett.getWin());
		bg_au.stop();
		for (JLabel lb : win_lb)
			c.add(lb, (Integer) 404);
		c.add(winTeam_lb, (Integer) 405);
		getAudio("�C������").play();
	}

	public void moveCard(Moveable m) {
		m.start();
		for (int i = 0; i <= m.js0; i++) {
			m.setUp(i);
			try {
				Thread.sleep(Setting.anmSpc);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		try {
			Thread.sleep(Setting.movePauseTime);
		} catch (InterruptedException e1) {
			e1.printStackTrace();
		}
		for (int i = 0; i <= m.js1; i++) {
			m.setDown(i);
			try {
				Thread.sleep(Setting.anmSpc);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		m.finish();
	}

	public void moveCards(Moveable[] m) {

		// ����|
		for (int i = 0; i < m.length; i++) {
			Move mov = m[i].move0;
			if (m[i].hasSetMx)
				continue;
			int minmx = m[i].mx;
			int maxmx = m[i].mx;
			int t = 1;
			for (int j = i + 1; j < m.length; j++) {
				Move $mov = m[j].move0;
				if (mov.seat == $mov.seat) {
					t++;
					minmx = Math.min(minmx, m[j].mx);
					maxmx = Math.max(maxmx, m[j].mx);
				}
			}
			int s = 0;
			m[i].setMx(minmx, maxmx, 0, t);
			for (int j = i + 1; j < m.length; j++) {
				Move $mov = m[j].move0;
				if (mov.seat == $mov.seat) {
					s++;
					m[j].setMx(minmx, maxmx, s, t);
					m[j].hasSetMx = true;
				}
			}
		}
		
		for (int i = 0; i < m.length; i++) {
			Move mov = m[i].move0;
			if (!mov.shouldShine)
				continue;
			for (int j = i + 1; j < m.length; j++) {
				Move $mov = m[j].move0;
				if (mov.seat == $mov.seat && mov.loc.equals($mov.loc))
					$mov.shouldShine = false;
			}

			mov = m[i].move1;
			if (!mov.shouldShine)
				continue;
			for (int j = i + 1; j < m.length; j++) {
				Move $mov = m[j].move1;
				if (mov.seat == $mov.seat && mov.loc.equals($mov.loc))
					$mov.shouldShine = false;
			}
		}

		
		//--�ʵe�}�l
		for (int l = 0; l < m.length; l++)
			m[l].start();

		for (int i = 0; i <= m[0].js0; i++) {
			for (int l = 0; l < m.length; l++)
				m[l].setUp(i);
			try {
				Thread.sleep(Setting.anmSpc);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		try {
			Thread.sleep(Setting.movePauseTime);
		} catch (InterruptedException e1) {
			e1.printStackTrace();
		}

		for (int i = 0; i <= m[0].js1; i++) {
			for (int l = 0; l < m.length; l++)
				m[l].setDown(i);
			try {
				Thread.sleep(Setting.anmSpc);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		for (int l = 0; l < m.length; l++)
			m[l].finish();
	}

	public String cardsToString(ArrayList<Integer> li) {
		String str = "";
		for (int i : li)
			str += i + ",";
		return str;
	}

	// ---------------------------------------------------------

	public abstract class CardPanel extends JPanel {

		public JLabel main_lb;
		public DarkLabel dark_lb;
		public int clickable = 0; // -1���i�� 0�w�] 1�i��
		public String message = "-1"; // �Y�i��A�h���U����ǰe���T���A-1�L�T��

		public CardPanel(int w, int h) {
			setLayout(null);
			setVisible(true);
			setSize(w, h);
			setOpaque(false);
			main_lb = new JLabel();
			main_lb.setBounds(0, 0, w, h);
			main_lb.setVisible(true);
			main_lb.setOpaque(false);
			main_lb.setHorizontalAlignment(JLabel.CENTER);
			main_lb.setVerticalAlignment(JLabel.CENTER);
			dark_lb = new DarkLabel(w, h);
		}

		public void show(int index) {
			add(main_lb, 0);
			add(dark_lb, 0);
			c.add(this, (Integer) index);
		}

	}

	class HandcardPanel extends CardPanel {

		public int id = -1; // id=-1��ܵL�P
		public boolean clickable = false;
		public boolean isSelectedd = false;

		public HandcardPanel(int id) {
			super(sett.getFccWid(), sett.getFccHei());
			addMouseListener(hl);
			setId(id);
		}

		public void setId(int id) {
			this.id = id;
			if (id == -1)
				main_lb.setIcon(null);
			else
				main_lb.setIcon((ImageIcon) in("gamecard/" + Tool.toFileId(id)));
		}

		public void setClickable(boolean d) {
			clickable = d;
			if (!d)
				dark_lb.setIcon(null);
			setLocation(sett.getHandcardX(getN(), game.handcards.size(), -1), sett.getHandcardY(d));
		}

		public int getN() {
			return game.handcards.indexOf(this);
		}
	}

	class FunccardPanel extends CardPanel {

		private int id = -1;
		private boolean dark = false;
		private boolean clickable = false; // �o�O�ΨӧP�_�O�_�i�ѯ}��
		public int arr0, arr1; // �b�Y�_�I�M���I

		public FunccardPanel(int id, int ar0, int ar1) {
			super(sett.getFccWid(), sett.getFccHei());
			addMouseListener(fl);
			setId(id);
			arr0 = ar0;
			arr1 = ar1;
		}

		public void setId(int id) {
			this.id = id;
			if (id == -1)
				main_lb.setIcon(null);
			if (id < 100) { // ��P
				main_lb.setIcon((ImageIcon) in("gamecard/" + Tool.toFileId(id)));
				if (dark)
					dark_lb.setIcon((ImageIcon) in("gamecard/black"));
				else
					dark_lb.setIcon(null);
			} else { // �ޯ�
				String ch = Skill.getChr(id);
				main_lb.setIcon((ImageIcon) in("skill/" + ch));
			}
		}

		public void setDark(boolean d) {
			dark = d;
			if (id != -1) {
				if (dark)
					dark_lb.setIcon((ImageIcon) in("gamecard/black"));
				else
					dark_lb.setIcon(null);
			}
		}

		public void setClickable(boolean c) {
			clickable = c;
			if (id != -1) {
				if (clickable)
					dark_lb.setIcon(null);
				else
					dark_lb.setIcon((ImageIcon) in("gamecard/black"));

			}
		}

	}

	class CharacterPanel extends CardPanel {

		class ListenLabel extends JLabel {

			CharacterPanel superpn;

			public ListenLabel(MouseAdapter ma, CharacterPanel superpn) {
				setBounds(superpn.getBounds());
				setVisible(true);
				setOpaque(false);
				addMouseListener(ma);
				this.superpn = superpn;
			}

			public ListenLabel(MouseAdapter ma, MouseMotionAdapter mma, CharacterPanel superpn) {
				setBounds(superpn.getBounds());
				setVisible(true);
				setOpaque(false);
				addMouseListener(ma);
				addMouseMotionListener(mma);
				this.superpn = superpn;
			}

		}

		public JLabel team_lb, dead_lb;
		public ListenLabel listen_lb;
		public IdealLabel username_lb, stus_lb, seat_lb;

		public int seat = -1;
		public String readChr = null; // �u�ꨤ��
		public String chr;
		public int status = 1; // ?1�� 2��3��
		public boolean isCov = false;

		public int idy = 0; // -1�L 0���� 1�� 2�x 3��
		public boolean isIdyCov = true;
		public boolean isShowingTeam = false;

		public CharacterPanel(int s) {
			super(sett.getChrWid(), sett.getChrHei());
			seat = s;
			setLocation(sett.getChrX(seat, game.plyCount), sett.getChrY(seat, game.plyCount));

			setChr("����");
			listen_lb = new ListenLabel(cl, cml, this);

			team_lb = new JLabel();
			team_lb.setHorizontalAlignment(JLabel.CENTER);
			team_lb.setVerticalAlignment(JLabel.CENTER);
			team_lb.setVisible(true);
			team_lb.setOpaque(false);
			team_lb.setSize(sett.getTeamWid(), sett.getTeamHei());
			team_lb.setLocation(sett.getTeamLoc(seat, game.plyCount));

			dead_lb = new JLabel();
			dead_lb.setVisible(true);
			dead_lb.setOpaque(false);
			dead_lb.setLocation(0, 0);
			dead_lb.setSize(sett.getChrWid(), sett.getChrHei());
			dead_lb.setHorizontalAlignment(JLabel.CENTER);
			dead_lb.setVerticalAlignment(JLabel.CENTER);

			username_lb = new IdealLabel(null, null);
			username_lb.bold = true;
			username_lb.setBounds(0, 0, sett.getChrWid(), sett.getChrHei() / 6);
			username_lb.setForeground(Color.ORANGE);
			username_lb.setHorizontalAlignment(JLabel.RIGHT);

			seat_lb = new IdealLabel("���a" + seat, null);
			seat_lb.bold = true;
			seat_lb.setBounds(0, sett.getChrHei() * 5 / 6, sett.getChrWid(), sett.getChrHei() / 6);
			seat_lb.setForeground(Color.WHITE);
			seat_lb.setHorizontalAlignment(JLabel.RIGHT);

			stus_lb = new IdealLabel(null, null);
			stus_lb.bold = true;
			stus_lb.setBounds(0, sett.getChrHei() * 4 / 6, sett.getChrWid(), sett.getChrHei() / 6);
			stus_lb.setForeground(Color.RED);
			stus_lb.setHorizontalAlignment(JLabel.RIGHT);
		}

		public void setStatus(int st, int idy, String c) {
			// st 2�� 3��
			// idy -1 �S����
			int f = -1;
			status = st;
			if (st == 2) {
				if (idy == -1)
					f = 0;
				else
					f = idy;
			} else if (st == 3)
				f = idy + 3;
			main_lb.setIcon((ImageIcon) in("character/dead" + c));
			if (st == 2) {
				getAudio("����/" + c + "���`").play();
				getAudio("���`").play();
			} else if (st == 3) {
				getAudio("����/" + c + "���`").play();
				getAudio("���`").play();
			}
			dead_lb.setIcon((ImageIcon) in("deadicon/" + f));
			game.ltds.get(seat).setIcon(null);
		}

		public void setChr(String c) {
			chr = c;
			main_lb.setIcon((ImageIcon) in("character/" + chr));
		}

		public void setClickable(int s) {
			if (status == 1) {
				clickable = s;
				if (s == 1 || s == 0)
					dark_lb.setIcon(null);
				else if (s == -1)
					dark_lb.setIcon((ImageIcon) in("character/black"));
			}
		}

		public void anmShowTeam(int idy, boolean isPublic) {
			@SuppressWarnings("unchecked")
			Runnable team_run = () -> {

				isShowingTeam = true;
				ArrayList<ImageIcon> imgs = null;
				imgs = (ArrayList<ImageIcon>) in("team/anm" + (isPublic ? idy : idy + 3));
				int size = imgs.size();
				for (int i = 0; i < size; i++) {
					team_lb.setIcon(imgs.get(i));
					try {
						Thread.sleep(Setting.anmSpc);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}

				try {
					Thread.sleep(500);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}

				for (int i = size - 1; i >= 0; i--) {
					team_lb.setIcon(imgs.get(i));
					try {
						Thread.sleep(Setting.anmSpc);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
				team_lb.setIcon(null);
				isShowingTeam = false;
			};
			new Thread(team_run, "½�}�����ʵe").start();
		}

		@SuppressWarnings("unchecked")
		public void open(boolean open, String chr) {
			ArrayList<ImageIcon> imgs;
			imgs = (ArrayList<ImageIcon>) in("character/openChr" + chr);
			int t = imgs.size();
			if (open) {
				for (int i = 0; i < t; i++) {
					main_lb.setIcon(imgs.get(i));
					try {
						Thread.sleep(Setting.anmSpc);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
				main_lb.setIcon((ImageIcon) in("character/" + chr));
			} else {
				for (int i = t - 1; i >= 0; i--) {
					main_lb.setIcon(imgs.get(i));
					try {
						Thread.sleep(Setting.anmSpc);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
				main_lb.setIcon((ImageIcon) in("character/����"));
			}
			imgs = null;
		}

		public void showTeam() {
			if (!isShowingTeam && idy != 0)
				team_lb.setIcon((ImageIcon) in("team/" + (isIdyCov ? idy + 3 : idy)));
		}

		public void removeTeam() {
			if (!isShowingTeam)
				team_lb.setIcon(null);
		}

		@Override
		public void show(int index) {
			add(main_lb, 0);
			add(dark_lb, 0);
			add(team_lb, 0);
			add(dead_lb, 0);
			add(stus_lb, 0);
			add(seat_lb, 0);
			add(username_lb, 0);
			c.add(this, (Integer) index);
			c.add(listen_lb, (Integer) 1000); // �������r
		}

		SpeakCanvas cv;
		JTextArea ta;
		Runnable speakTimer = () -> {
			try {
				Thread.sleep(5000);
				overSpeak();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		};
		Thread speak_th;

		class SpeakCanvas extends JComponent {

			public static final double deg = 30.0;
			public Dimension size;
			public int _h;

			public SpeakCanvas(Dimension d) {
				size = d;
				_h = (int) (size.width / Math.tan(Math.toRadians(deg)) / 14);
				setSize(d.width, d.height + _h);
				setLocation(sett.getChrX(seat, game.plyCount) + sett.getChrLng() / 3 - getWidth() * 3 / 14,
						sett.getChrY(seat, game.plyCount) + sett.getChrLng() / 3 - getHeight());
				setOpaque(false);
			}

			@Override
			public void paintComponent(Graphics g) {
				int w = size.width;
				g.setColor(new Color(0f, 0f, 0f, .75f));
				int[] x = { w / 7, w * 2 / 7, w * 3 / 14 };
				if (sett.isChrAtTop(seat, game.plyCount)) {
					int[] y = { _h, _h, 0 };
					g.fillPolygon(x, y, 3);
					g.fillRoundRect(0, _h, size.width, size.height, 10, 10);
				} else {
					int[] y = { size.height, size.height, size.height + _h };
					g.fillPolygon(x, y, 3);
					g.fillRoundRect(0, 0, size.width, size.height, 10, 10);
				}
			}

		}

		public void speak(String txt) {
			if (c.isAncestorOf(ta) || c.isAncestorOf(cv))
				overSpeak();

			ta = new JTextArea();
			ta.setLineWrap(true);
			ta.setEditable(false);
			ta.setOpaque(false);
			ta.setVisible(true);
			ta.setBackground(null);
			ta.setForeground(Color.WHITE);
			ta.setFont(new Font("�L�n������", Font.PLAIN, (int) (20 * sett.rate)));
			ta.setSize(getWidth() * 6 / 5, ta.getPreferredSize().height);
			ta.setText(txt);
			ta.setSize(getWidth() * 6 / 5, ta.getPreferredSize().height);
			cv = new SpeakCanvas(ta.getSize());
			if (sett.isChrAtTop(seat, game.plyCount))
				ta.setLocation(cv.getX(), cv.getY() + cv._h);
			else
				ta.setLocation(cv.getLocation());
			c.add(cv, (Integer) 1998);
			c.add(ta, (Integer) 1999);
			if (speak_th != null && speak_th.isAlive())
				speak_th.interrupt();
			speak_th = new Thread(speakTimer);
			speak_th.start();
		}

		public void overSpeak() {
			c.remove(ta);
			c.remove(cv);
			c.repaint();
		}

	}

	class GameButton extends JLabel {

		private boolean canClicked = false;
		public String text = null;
		public String message = "-1";

		public GameButton(String t) {
			text = t;
			setVisible(true);
			setOpaque(false);
			setHorizontalAlignment(JLabel.CENTER);
			setVerticalAlignment(JLabel.CENTER);
			setSize(sett.getBtnSize());
			addMouseListener(bl);
		}

		public void setClickable(boolean c) {
			canClicked = c;
			if (c)
				setIcon(((ImageIcon) in("btn/" + text)));// GameImage.get("btnImg")).get(text));
			else {
				setIcon(((ImageIcon) in("btn/" + text + "N")));
				message = "-1";
			}
		}

		public boolean getClickable() {
			return canClicked;
		}

		public void remove() {
			canClicked = false;
			c.remove(this);

		}

		public void show(boolean canClicked, int loc, String mess) {
			if (canClicked)
				message = mess;
			else
				message = "-1";
			setClickable(canClicked);
			setLocation(sett.getBtnLoc(loc));
			c.add(this, (Integer) 10);
		}
	}

	class IntelligenceLabel extends JLabel {

		public int id = -1; // �d�Pid 97�K�q 98���F�A��l½�}

		public IntelligenceLabel() {
			super();
			setOpaque(false);
			setVisible(true);
			setVerticalAlignment(JLabel.CENTER);
			setHorizontalAlignment(JLabel.CENTER);
			setSize(sett.getItlWid(), sett.getItlHei());
		}

		public void setId(int id) {
			this.id = id;
			if (id != -1)
				setIcon(Tool.fix((ImageIcon) in("gamecard/" + Tool.toFileId(id)), sett.getItlWid(), sett.getItlHei()));
		}

		public int getId() {
			return id;
		}

	}

	class ItlCardLabel extends GamecardLabel {

		public ItlCardLabel(int id, boolean aflag) {
			super(id);
			if (aflag)
				addMouseListener(il);
			else
				addMouseListener(il$);
		}
	}

	class GamecardLabel extends JLabel {
		public int id = -1;

		public GamecardLabel(int id) {
			setId(id);
			setVisible(true);
			setOpaque(false);
			setHorizontalAlignment(JLabel.CENTER);
			setVerticalAlignment(JLabel.CENTER);
			setSize(sett.getFccWid(), sett.getFccHei());
		}

		public void setId(int i) {
			id = i;
			if (id == -1)
				setIcon(null);
			else
				setIcon((ImageIcon) in("gamecard/" + Tool.toFileId(i)));// ci.stCards.get(Tool.toFileId(id)));
		}

		public int getId() {
			return id;
		}
	}

	class LtdLabel extends JLabel {

		public int ltd = 0;

		public LtdLabel() {
			setVerticalAlignment(JLabel.CENTER);
			setHorizontalAlignment(JLabel.CENTER);
			setOpaque(false);
			setVisible(true);
			setSize(sett.getChrLng(), sett.getChrLng());
		}
	}

	class ItlPanel extends JPanel {

		protected JLabel title_lb, comp_lb;
		public ArrayList<ItlCardLabel> cards = new ArrayList<>();

		public ItlPanel(String t, String cs) {
			// t:title
			// cs:cards
			setLayout(null);
			setVisible(true);
			setOpaque(true);
			setBounds(sett.getItlFld());
			title_lb = new JLabel("", JLabel.CENTER);
			title_lb.setVisible(true);
			title_lb.setOpaque(true);
			title_lb.setBounds(0, 0, sett.getItlFldWid(), sett.getItlFldTtHei());
			title_lb.setForeground(Color.BLACK);
			title_lb.setBackground(Color.ORANGE);
			title_lb.setFont(new Font("�L�n������", Font.BOLD, Math.round(sett.getItlFldTtHei() * 0.8f)));
			comp_lb = new JLabel();
			comp_lb.setVisible(true);
			comp_lb.setOpaque(true);
			comp_lb.setBounds(0, sett.getItlFldTtHei(), sett.getItlFldWid(), sett.getItlFldHei() - sett.getItlFldTtHei());
			comp_lb.setBackground(Color.YELLOW);
			setTitle(t);
			addIn();
			addCards(cs);
		}

		public void addIn() {
			add(title_lb, 0);
			add(comp_lb, 0);
		}

		public void setTitle(String t) {
			title_lb.setText(t);
		}

		public String getTitle() {
			return title_lb.getText();
		}

		public void addCards(String cs) {
			String[] cd = cs.split(",");
			for (String id : cd)
				cards.add(new ItlCardLabel(Integer.parseInt(id), true));
			int t = cards.size();
			for (int i = 0; i < cards.size(); i++)
				cards.get(i).setLocation(sett.getImItlX(i, t, -1), sett.getImItlY());
			for (ItlCardLabel il : cards)
				add(il, 0);
		}
	}

	class NormalListener extends MouseAdapter {
		@Override
		public void mouseEntered(MouseEvent e) {
			JLabel lb = (JLabel) e.getSource();
			lb.setBackground(Setting.onEntered_col);
			bo_au.play();
			chrComp_pn = new CharacterComp(lb.getName());
			c.add(chrComp_pn, (Integer) 1000);
		}

		@Override
		public void mouseClicked(MouseEvent e) {
			if (!e.isMetaDown())
				synchronized (sit) {
					JLabel lb = (JLabel) e.getSource();
					switch (sit) {
					case "30":
						// ��ܨ����Ĳ�o
						timeStop();
						sit = "31";
						print("��ܨ���:" + lb.getName());
						c.remove(chooChr_pn);
						c.remove(chrComp_pn);
						c.repaint();
						break;
					}
				}
		}

		@Override
		public void mouseExited(MouseEvent e) {
			JLabel lb = (JLabel) e.getSource();
			lb.setBackground(Setting.onExited_col);
			c.remove(chrComp_pn);
			c.repaint();
		}
	}

	class HandcardListener extends MouseAdapter {

		int selected = -1; // �d�P�Ǹ�!!

		@Override
		public void mouseEntered(MouseEvent e) {
			HandcardPanel hl = (HandcardPanel) e.getSource();
			int t = game.handcards.size();
			for (HandcardPanel lb : game.handcards) {
				lb.setLocation(sett.getHandcardX(lb.getN(), t, hl.getN()), lb.getY());
			}
			pa_au.play();
		}

		@Override
		public void mousePressed(MouseEvent e) { // �M�w�O�_�ܷt
			if (!e.isMetaDown())
				synchronized (sit) {
					if (isSelectingHc == 1) { // �ۥѥX�P���q
						HandcardPanel hl = (HandcardPanel) e.getSource();
						if (!hl.clickable)
							hl.dark_lb.setIcon((ImageIcon) in("gamecard/black"));
					}
				}
		}

		@Override
		public void mouseReleased(MouseEvent e) { // �^�_�쪬�N�O�F
			if (!e.isMetaDown()) {
				HandcardPanel hl = (HandcardPanel) e.getSource();
				if (!hl.clickable)
					hl.dark_lb.setIcon(null);
			}
		}

		@Override
		public void mouseClicked(MouseEvent e) { // �M�w�O�_�ǰe�T��
			if (!e.isMetaDown())
				synchronized (sit) {
					HandcardPanel hl = (HandcardPanel) e.getSource();

					if (isSelectingHc == 1 || isSelectingHc == 100) { // �ۥѥX�P���q
						if (hl.clickable) { // �o�i�i��
							if (selected == hl.getN()) { // ���ƿ�����i��w�A�N��N�O����
								for (HandcardPanel lb : game.handcards)
									lb.setLocation(sett.getHandcardX(lb.getN(), game.handcards.size(), selected),
											sett.getHandcardY(lb.clickable));
								refreshCharacterClickable(); // �쥻�ΤF��w��@�b�A�{�b���ΤF���N��
								selected = -1;
								ccl_btn.remove();
								c.repaint();
							} else { // �S�����ƿ�����i��w�A�N��N�O��

								selected = hl.getN();
								int t = game.handcards.size();
								for (int i = 0; i < t; i++)
									if (selected == i)
										game.handcards.get(i).setLocation(sett.getHandcardX(i, t, selected),
												sett.getHandcardY(true));
									else
										game.handcards.get(i).setLocation(sett.getHandcardX(i, t, selected),
												sett.getHandcardY(false));
								String mess = isSelectingHc == 1 ? "�ϥΤ�P:" : "��ܤ�P:";
								print(mess + hl.id);
							}
						} /* �o�i���i�� */
						else {
							if (selected != -1 && selected != hl.getN()) { // ��쥻�ɰ_�Ӫ��P�˦^�h
								/* ��F���O���e�諸���i */
								for (HandcardPanel lb : game.handcards)
									lb.setLocation(sett.getHandcardX(lb.getN(), game.handcards.size(), hl.getN()),
											sett.getHandcardY(lb.clickable));
								refreshCharacterClickable(); // �쥻�ΤF��w��@�b�A�{�b���ΤF���N��
								selected = -1;
								if (c.isAncestorOf(ccl_btn)) {
									ccl_btn.remove();
									c.repaint();
								}
							}
						}
					} else if (isSelectingHc == 2 && hl.clickable) {
						print("��ܤ�P:" + hl.id);
					} else if (isSelectingHc == 3) {
						if (selects.size() < sMax && list.contains(String.valueOf((hl.id)))) {
							hl.setVisible(false);
							selects.add(hl.id);
							if (selects.size() >= Math.max(sMin, 1))
								ok_btn.show(true, 1, "!p");
							if (selects.size() != 0)
								undo_btn.show(true, 2, "!u");
						}
					}
				}
		}

		@Override
		public void mouseExited(MouseEvent e) {
			int t = game.handcards.size();
			for (HandcardPanel lb : game.handcards) {
				lb.setLocation(sett.getHandcardX(lb.getN(), t, -1), lb.getY());
			}
		}
	}

	class FunccardListener extends MouseAdapter {

		ArrayList<ArrowLabel> arws = new ArrayList<>();

		public void mouseEntered(MouseEvent e) {

			FunccardPanel lb = (FunccardPanel) e.getSource();
			synchronized (arws) {
				if (!isQueuing) {
					int n = game.funccards.indexOf(lb);
					if (!isSelectingCard) {
						if (lb.arr0 == -1 || lb.arr1 == -1)
							return;
						int x0, x1, y0, y1; // �_���I���ߦ�m~!
						// �Q�諸�P�O�ĴX�i
						if (n == -1)
							new LogicException("������������F�@�i�s���� " + lb.id + " ���P�A��  game.fc �L���P�C").printStackTrace();
						if (lb.arr0 >= 0 && lb.arr0 < 10) { // �o�O���a�츹
							x0 = sett.getChrX(lb.arr0, game.plyCount) + (sett.getChrWid()) / 2;
							y0 = sett.getChrY(lb.arr0, game.plyCount) + (sett.getChrHei()) / 2;
						} else if (lb.arr0 >= 200 && lb.arr0 < 300) { // �ݵ���d�P
							x0 = sett.getFccX(lb.arr0 - 200, game.funccards.size(), n, lb.arr0 - 200) + sett.getFccWid() / 2; // ���ɳo�i�d�P�n�Q�E�J!!
							y0 = sett.getFccY() + sett.getFccHei() / 2;
						} else {
							new AnimationException("�b�Yø�ϮɥX�{���~�N�X arr0 = " + lb.arr0).printStackTrace();
							return;
						}
						if (lb.arr1 >= 0 && lb.arr1 < 10) { // �o�O���a�츹
							x1 = sett.getChrX(lb.arr1, game.plyCount) + (sett.getChrWid()) / 2;
							y1 = sett.getChrY(lb.arr1, game.plyCount) + (sett.getChrHei()) / 2;
						} else if (lb.arr1 >= 200 && lb.arr1 < 300) { // �ݵ���d�P
							x1 = sett.getFccX(lb.arr1 - 200, game.funccards.size(), n, lb.arr1 - 200) + sett.getFccWid() / 2; // ���ɳo�i�d�P�n�Q�E�J!!
							y1 = sett.getFccY() + sett.getFccHei() / 2;
						} else if (lb.arr1 == 300) {// ����
							x1 = itl_lb.getX() + sett.getItlWid() / 2;
							y1 = itl_lb.getY() + sett.getItlHei() / 2;
						} else {
							new AnimationException("�b�Yø�ϮɥX�{���~�N�X arr1 = " + lb.arr1).printStackTrace();
							return;
						}

						int vx = x0 - x1, vy = y0 - y1; // �p�b���ݭn���V�q(�_�I����I)
						double l = Math.sqrt(vx * vx + vy * vy); // �`�V�q����
						int t = Math.round((float) (l / new ArrowLabel().getSpace())); // �|���X�Ӥp�b��
						double s = l / t; // �վ�L��b��������
						float dx, dy;
						if (x1 == x0)
							dx = 0;
						else
							dx = (float) (s * (x1 - x0) / l);
						if (y1 == y0)
							dy = 0;
						else
							dy = (float) (s * (y1 - y0) / l); // �b�y�V�q����

						arws = new ArrayList<>();
						for (int ar = 0; ar <= t; ar++) {
							ArrowLabel al = new ArrowLabel(vx, vy);
							al.setLocation(Math.round(x0 + dx * ar), Math.round(y0 + dy * ar));
							arws.add(al);
						}
					}

					int size = game.funccards.size();
					for (int i = 0; i < size; i++)
						if (lb.arr1 >= 200 && lb.arr1 < 300)
							game.funccards.get(i).setLocation(sett.getFccX(i, size, n, lb.arr1 - 200), sett.getFccY());
						else
							game.funccards.get(i).setLocation(sett.getFccX(i, size, n), sett.getFccY());

					if (!isSelectingCard)
						for (ArrowLabel al : arws) {
							try {
								c.add(al, (Integer) (400));
							} catch (ArrayIndexOutOfBoundsException e1) {
								e1.printStackTrace();
							}
						}
				}
			}
			pa_au.play();
		}

		@Override
		public void mouseClicked(MouseEvent e) {
			if (!e.isMetaDown())
				synchronized (sit) {
					FunccardPanel fl = (FunccardPanel) e.getSource();
					if (isSelectingCard && fl.clickable)
						print("��ܥd�P:" + game.funccards.indexOf(fl));
				}
		}

		@Override
		public void mouseExited(MouseEvent e) {

			synchronized (arws) {
				if (!arws.isEmpty()) {
					if (!isSelectingCard) {
						for (ArrowLabel al : arws)
							try {
								c.remove(al);
							} catch (ArrayIndexOutOfBoundsException e1) {
								e1.printStackTrace();
							}
						arws.clear();
						c.repaint();
					}
					int t = game.funccards.size();
					for (int i = 0; i < t; i++)
						game.funccards.get(i).setLocation(sett.getFccX(i, t, -1), sett.getFccY());
				}
			}
		}
	}

	class CharacterListener extends MouseAdapter {

		@Override
		public void mouseEntered(MouseEvent e) {
			CharacterPanel lb = ((CharacterPanel.ListenLabel) e.getSource()).superpn;
			if (lb.status == 1) {
				if (lb.clickable == 1) {
					lb.dark_lb.setIcon(((ImageIcon) in("character/white")));
					pa_au.play();
				} else if (lb.clickable != -1) {
					if (lb.readChr != null)
						lb.main_lb.setIcon((ImageIcon) in("character/" + lb.readChr));
					lb.showTeam();
				}
			}
			if (lb.clickable == 0) {
				if (!lb.chr.equals("����")) {
					chrComp_pn = new CharacterComp(lb.chr);
					c.add(chrComp_pn, (Integer) 1000);
				} else if (lb.readChr != null) {
					chrComp_pn = new CharacterComp(lb.readChr);
					c.add(chrComp_pn, (Integer) 1000);
				}
			}
		}

		@Override
		public void mouseClicked(MouseEvent e) {
			if (!e.isMetaDown())
				synchronized (sit) {
					CharacterPanel cl = ((CharacterPanel.ListenLabel) e.getSource()).superpn;
					if (cl.clickable == 1)
						print("��ܪ��a:" + game.chrs.indexOf(cl));
				}
		}

		@Override
		public void mouseExited(MouseEvent e) {
			CharacterPanel lb = ((CharacterPanel.ListenLabel) e.getSource()).superpn;
			if (lb.status == 1) {
				if (lb.clickable == 1)
					lb.dark_lb.setIcon(null);
				if (lb.seat == game.mySeat && lb.chr.equals("����"))
					lb.main_lb.setIcon((ImageIcon) in("character/����"));
				lb.removeTeam();
			}
			if (c.isAncestorOf(chrComp_pn)) {
				c.remove(chrComp_pn);
				c.repaint();
			}
		}
	}

	class CharacterComp extends JPanel {

		String chr;
		public JTextPane skillcomp;
		JLabel bg_lb;

		public CharacterComp(String c) {
			chr = c;
			skillcomp = new JTextPane();
			skillcomp.setVisible(true);
			skillcomp.setOpaque(false);
			skillcomp.setLocation(0, 0);
			skillcomp.setEditable(false);
			skillcomp.setSize(300, skillcomp.getPreferredSize().height);
			skillcomp.setDocument(CharacterData.getDoc(chr, sett));
			skillcomp.setSize(300, skillcomp.getPreferredSize().height);
			bg_lb = new JLabel(Tool.black(skillcomp.getSize(), 255 / 2));
			bg_lb.setVisible(true);
			bg_lb.setSize(skillcomp.getSize());
			bg_lb.setLocation(0, 0);
			bg_lb.setOpaque(false);
			setOpaque(false);
			setVisible(true);
			setLayout(null);
			setSize(skillcomp.getSize());
			add(bg_lb, 0);
			add(skillcomp, 0);
		}

	}

	class CharacterMoveListener extends MouseMotionAdapter {

		@Override
		public void mouseMoved(MouseEvent e) {
			CharacterPanel lb = ((CharacterPanel.ListenLabel) e.getSource()).superpn;
			if (lb.clickable == 0 && chrComp_pn != null) {
				if (c.isAncestorOf(chrComp_pn)) {
					int x = e.getX() + lb.getX() + 5;
					if (x + chrComp_pn.getWidth() > sett.scnWidth - sett.getNmlSpc())
						x -= chrComp_pn.getWidth() - 10;
					int y = e.getY() + lb.getY() - chrComp_pn.getHeight() + 5;
					if (y < sett.getNmlSpc())
						y += chrComp_pn.getHeight() - 10;
					chrComp_pn.setLocation(x, y);
				}
			}
		}
	}

	class ButtonListener extends MouseAdapter {

		@Override
		public void mouseEntered(MouseEvent e) {
			GameButton btn = (GameButton) e.getSource();
			if (btn.canClicked)
				btn.setIcon(((ImageIcon) in("btn/" + btn.text + "C")));
			bo_au.play();
		}

		@Override
		public void mouseClicked(MouseEvent e) {
			synchronized (sit) {
				if (!e.isMetaDown()) {
					GameButton btn = (GameButton) e.getSource();
					String mess = btn.message;
					if (!mess.equals("-1")) {
						if (mess.startsWith("!")) {
							if (mess.equals("!p")) {
								if (isSelectingHc == 3) {
									print("��ܦh�i��P:" + cardsToString(selects));
								} else if (isSelectingItl == 2) {
									print("��ܦh�i����:" + cardsToString(selects));
								}
							} else if (mess.equals("!u")) {
								selects.clear();
								undo_btn.show(false, 2, null);
								ok_btn.show(false, 1, null);
								if (isSelectingHc == 3) {
									for (HandcardPanel $lb : game.handcards)
										$lb.setVisible(true);
								} else if (isSelectingItl == 2) {
									for (ItlCardLabel $lb : itl_pn.cards)
										$lb.setVisible(true);
								}
							}
						} else
							print(mess);
					}
				}
			}
		}

		@Override
		public void mouseExited(MouseEvent e) {
			GameButton btn = (GameButton) e.getSource();
			if (btn.canClicked)
				btn.setIcon(((ImageIcon) in("btn/" + btn.text)));// GameImage.get("btnImg")).get(btn.text));
		}
	}

	class ItlCardListener extends MouseAdapter {

		public boolean isUsable = false;

		public ItlCardListener(boolean b) {
			isUsable = b;
		}

		@Override
		public void mouseEntered(MouseEvent e) {
			ItlCardLabel il = (ItlCardLabel) e.getSource();
			if (itl_pn != null) {
				if (itl_pn.isAncestorOf(il)) {
					int t = itl_pn.cards.size();
					int f = itl_pn.cards.indexOf(il);
					for (int i = 0; i < t; i++)
						itl_pn.cards.get(i).setLocation(sett.getImItlX(i, t, f), sett.getImItlY());
					pa_au.play();
				}
			} else if (seeItl_pn != null) {
				if (seeItl_pn.isAncestorOf(il)) {
					int t = seeItl_pn.cards.size();
					int f = seeItl_pn.cards.indexOf(il);
					for (int i = 0; i < t; i++)
						seeItl_pn.cards.get(i).setLocation(sett.getImItlX(i, t, f), sett.getImItlY());
					pa_au.play();
				}
			}
		}

		@Override
		public void mouseClicked(MouseEvent e) {
			if (isUsable && !e.isMetaDown())
				synchronized (sit) {
					ItlCardLabel il = (ItlCardLabel) e.getSource();
					if (isSelectingItl == 1)
						print("��ܱ���:" + il.getId());
					else if (isSelectingItl == 2) {
						if (selects.size() < sMax && list.contains(String.valueOf(il.id))) {
							il.setVisible(false);
							selects.add(il.id);
							if (selects.size() >= Math.max(sMin, 1))
								ok_btn.show(true, 1, "!p");
							if (selects.size() != 0)
								undo_btn.show(true, 2, "!u");
						}
					}
				}
		}

		@Override
		public void mouseExited(MouseEvent e) {
			ItlCardLabel il = (ItlCardLabel) e.getSource();
			if (itl_pn != null) {
				if (itl_pn.isAncestorOf(il)) {
					int t = itl_pn.cards.size();
					for (int i = 0; i < t; i++)
						itl_pn.cards.get(i).setLocation(sett.getImItlX(i, t, -1), sett.getImItlY());
				}
			} else if (seeItl_pn != null) {
				if (seeItl_pn.isAncestorOf(il)) {
					int t = seeItl_pn.cards.size();
					for (int i = 0; i < t; i++)
						seeItl_pn.cards.get(i).setLocation(sett.getImItlX(i, t, -1), sett.getImItlY());
				}
			}
		}
	}

	class LotteryPanel extends JPanel {

		ArrayList<GamecardLabel> lotts = new ArrayList<>();

		public LotteryPanel(int t) {
			setLayout(null);
			setBackground(Color.GRAY);
			setOpaque(true);
			setVisible(true);
			setBounds(sett.getLotFld(t));
			for (int i = 0; i < t; i++) {
				ItlCardLabel il = new ItlCardLabel(100, false);
				il.setLocation(sett.getLotLoc(i, t));
				lotts.add(il);
				c.add(il, (Integer) 299);
			}
		}

		@SuppressWarnings("unchecked")
		public void getCard(int who, int id, String loc, int cc, String sound1, String sound2) {

			ArrayList<ImageIcon> imgs1 = (ArrayList<ImageIcon>) in("gamecard/cardByLottery" + Tool.toFileId(id));
			HashMap<Integer, ImageIcon> imgs2 = (HashMap<Integer, ImageIcon>) in("gamecard/card_size" + Tool.toFileId(id));
			GamecardLabel gl = lotts.get(0);
			final int sw = sett.getFccWid();
			final int sh = sett.getFccHei();
			final int fh = sett.getImHei();
			final int fw = Math.round((float) fh * sw / sh);
			final int sx = gl.getX();
			final int sy = gl.getY();
			final int fy = sett.getImY(who, game.plyCount);
			final int fx = sett.getImX(who, game.plyCount, loc) + (sett.getImWid(who, game.plyCount, loc) - fw) / 2;

			final int js = Setting.anmDownTime / Setting.anmSpc;
			final float dw = (float) (fw - sw) / js;
			final float dh = (float) (fh - sh) / js;
			final float dx = (float) (fx - sx) / js;
			final float dy = (float) (fy - sy) / js;

			JLabel im = null;
			switch (loc) {
			case "r":
				im = game.im_r_lb.get(who);
				break;
			case "b":
				im = game.im_b_lb.get(who);
				break;
			case "k":
				im = game.im_k_lb.get(who);
				break;
			default:
				new LogicException("���~�� loc: " + loc);
				return;
			}
			AudioClip au1 = null, au2 = null;
			if (!sound1.equals("-1"))
				au1 = getAudio(sound1);
			if (!sound2.equals("-1"))
				au2 = getAudio(sound2);

			for (ImageIcon img : imgs1) { // ��½�}�ʧ@
				gl.setIcon(img);
				try {
					Thread.sleep(Setting.anmSpc);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}

			try {
				Thread.sleep(Setting.anmUpAndDownPauseTime);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}

			for (int j = 0; j <= js; j++) {
				int h = Math.round(sh + dh * j);
				gl.setIcon(imgs2.get(h));
				gl.setBounds(Math.round(sx + dx * j), Math.round(sy + dy * j), Math.round(sw + dw * j), h);
				try {
					Thread.sleep(Setting.anmSpc);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
			c.remove(gl);
			c.repaint();
			if (au1 != null)
				au1.play();
			if (au2 != null)
				au2.play();
			shine(im, cc, im.getBackground());
			lotts.remove(0);
			if (lotts.isEmpty())
				try {
					Thread.sleep(800);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
		}
	}

	abstract class ShowCardPanel extends JPanel {

		protected GamecardLabel card;
		protected int id;
		protected JTextArea comp = new JTextArea();

		public ShowCardPanel(int id) {
			this.id = id;
			setLayout(null);
			setBackground(Color.ORANGE);
			setBounds(sett.getSeeItlFld());
			setVisible(true);
			setOpaque(true);

			card = new GamecardLabel(id);
			card.setBounds(sett.getSeeItlFldSpc(), (sett.getSeeItlFldHei() - sett.getFccHei()) / 2, sett.getFccWid(),
					sett.getFccHei());

			comp.setVisible(true);
			comp.setOpaque(false);
			comp.setWrapStyleWord(true);
			comp.setLineWrap(true);
			comp.setForeground(Color.BLACK);
			comp.setFont(sett.getSeeItlTxtFont());
		}

		@Override
		public void show() {

		}

	}

	class TestPanel extends ShowCardPanel {

		JLabel btn1 = new JLabel("", JLabel.CENTER), btn2 = new JLabel("", JLabel.CENTER);

		public TestPanel(int id, int idy, int c, int light) {

			super(id);
			// idy�ձ��������O: 1��� 2�x�� 3��o
			// c: 2���i�P -1��@�i�P
			// light�Ӧ^��������: 1��|��P 2�^����r 3�ҥi(�Ѱ�)

			comp.setBounds(sett.getSeeItlBtnX(), sett.getSeeItlFldSpc2(), sett.getSeeItlTxtWid(), sett.getTestCompHei());

			btn1.setVisible(true);
			btn1.setOpaque(true);
			btn1.setBounds(sett.getSeeItlBtnX(),
					sett.getSeeItlFldHei() - sett.getSeeItlFldSpc2() - sett.getSeeItlBtnHei() * 2 - sett.getNmlSpc(),
					sett.getSeeItlTxtWid(), sett.getSeeItlBtnHei());
			btn1.setForeground(Color.WHITE);
			btn1.setFont(sett.getSeeItlBtnFont());

			btn2.setVisible(true);
			btn2.setOpaque(true);
			btn2.setBounds(sett.getSeeItlBtnX(), sett.getSeeItlFldHei() - sett.getSeeItlFldSpc2() - sett.getSeeItlBtnHei(),
					sett.getSeeItlTxtWid(), sett.getSeeItlBtnHei());
			btn2.setForeground(Color.WHITE);
			btn2.setFont(sett.getSeeItlBtnFont());

			if (c == 2) {
				btn1.setText("���i�P");
				btn2.setText("���ڬO�ש�");
			} else if (c == -1) {
				btn1.setText("��@�i��P");
				btn2.setText("�ڬO�@�Ӧn�H");
			} else
				new LogicException("���~���ձ�����: " + c).printStackTrace();

			switch (idy) {
			case 1:
				if (c == 2)
					comp.setText("�p�G�A�O���Խu�����a�A�A���i�P�A�_�h�^�� \"���ڬO�ש�\"");
				else if (c == -1)
					comp.setText("�p�G�A�O���Խu�����a�A�A��@�i��P�A�_�h�^�� \"�ڬO�@�Ӧn�H\"");
				break;
			case 2:
				if (c == 2)
					comp.setText("�p�G�A�O�x���B�����a�A�A���i�P�A�_�h�^�� \"���ڬO�ש�\"");
				else if (c == -1)
					comp.setText("�p�G�A�O�x���B�����a�A�A��@�i��P�A�_�h�^�� \"�ڬO�@�Ӧn�H\"");
				break;
			case 3:
				if (c == 2)
					comp.setText("�p�G�A�O����o�����a�A�A���i�P�A�_�h�^�� \"���ڬO�ש�\"");
				else if (c == -1)
					comp.setText("�p�G�A�O����o�����a�A�A��@�i��P�A�_�h�^�� \"�ڬO�@�Ӧn�H\"");
				break;
			default:
				new LogicException("���~���ձ�����: " + idy).printStackTrace();
				break;
			}

			switch (light) {
			case 1:
				btn1.addMouseListener(sl);
				btn1.setBackground(Setting.onExited_col);
				btn2.setBackground(Color.DARK_GRAY);
				break;
			case 2:
				btn2.addMouseListener(sl);
				btn2.setBackground(Setting.onExited_col);
				btn1.setBackground(Color.DARK_GRAY);
				break;
			case 3:
				btn1.addMouseListener(sl);
				btn2.addMouseListener(sl);
				btn1.setBackground(Setting.onExited_col);
				btn2.setBackground(Setting.onExited_col);
				break;
			default:
				new LogicException("���~��light: " + light).printStackTrace();
				break;
			}
		}

		@Override
		public void show() {
			sl.isTesting = true;
			add(comp);
			add(btn1);
			add(btn2);
			add(card);
			c.add(this, (Integer) 401);
		}

	}

	class ShowCardListener extends MouseAdapter {

		boolean isTesting;

		public void mouseEntered(MouseEvent e) {
			JLabel btn = (JLabel) e.getSource();
			if (!btn.getBackground().equals(Color.DARK_GRAY))
				btn.setBackground(Setting.onEntered_col);
			getAudio("�i");
		}

		@Override
		public void mouseClicked(MouseEvent e) {
			if (!e.isMetaDown())
				synchronized (sit) {
					JLabel btn = (JLabel) e.getSource();
					if (isTesting) { // �ձ�FLD
						if (!btn.getBackground().equals(Color.DARK_GRAY)) {
							if (btn == test_pn.btn1)
								print("�^���ձ�:1");
							else if (btn == test_pn.btn2)
								print("�^���ձ�:2");
							else
								new LogicException("�_�Ǫ����s?!").printStackTrace();
						}
					}
				}
		}

		@Override
		public void mouseExited(MouseEvent e) {
			JLabel btn = (JLabel) e.getSource();
			if (!btn.getBackground().equals(Color.DARK_GRAY))
				btn.setBackground(Setting.onExited_col);
		}

	}

	class SeeCardPanel extends ShowCardPanel {

		IdealLabel btn = new IdealLabel("�T�{", "�[�ݵ���");

		public SeeCardPanel(int id, String txt) {
			super(id);
			comp.setText(txt);
			comp.setBounds(sett.getSeeItlBtnX(), sett.getSeeItlFldSpc(), sett.getSeeItlTxtWid(), sett.getSeeCardCompHei());

			btn.setBounds(sett.getSeeItlBtnX(), sett.getSeeItlFldHei() - sett.getSeeItlBtnHei() - sett.getSeeItlFldSpc(),
					sett.getSeeItlTxtWid(), sett.getSeeItlBtnHei());
		}

		@Override
		public void show() {
			add(comp);
			add(btn);
			add(card);
			c.add(this, (Integer) 401);
		}
	}

	class ImformLabel extends JLabel {

		public String mess = null;
		public String color = null;

		public ImformLabel(int s, String col) {
			super("0", JLabel.CENTER);
			setVisible(true);
			setOpaque(true);
			setForeground(Color.WHITE);
			setFont(sett.getImFont());
			mess = "�D�зǰT��:�[�ݱ���:" + s;
			color = col;
			switch (color) {
			case "r":
				setBackground(Setting.imRed);
				break;
			case "b":
				setBackground(Setting.imBlue);
				break;
			case "k":
				setBackground(Setting.imBlack);
				break;
			case "h":
				setBackground(Setting.imHand);
				break;
			}
			setBounds(sett.getImX(s, game.plyCount, color), sett.getImY(s, game.plyCount),
					sett.getImWid(s, game.plyCount, color), sett.getImHei());
			setVisible(true);
			setOpaque(true);
			setForeground(Color.WHITE);
			setFont(sett.getImFont());
			setHorizontalAlignment(JLabel.CENTER);
			setVerticalAlignment(JLabel.CENTER);
			addMouseListener(ml);
		}

	}

	class ImformListener extends MouseAdapter {

		@Override
		public void mouseClicked(MouseEvent e) {
			if (!e.isMetaDown()) {
				Object o = e.getSource();
				if (o instanceof ImformLabel) {
					ImformLabel lb = (ImformLabel) o;
					print(lb.mess);
				} else if (o instanceof TrashLabel) {
					TrashLabel lb = (TrashLabel) o;
					print(lb.mess);
				}
			}
		}
	}

	class SeeItlPanel extends ItlPanel {

		public JLabel close_btn;

		public SeeItlPanel(String t, String cs) {
			// t:title
			// cs:cards
			super(t, cs);
			close_btn.setVisible(true);
			close_btn.setOpaque(false);
			close_btn.setBounds(sett.getSeeItlFldCloseBtn());
			close_btn.addMouseListener(cll);
			comp_lb.setHorizontalAlignment(JLabel.LEFT);
		}

		@Override
		public void addIn() {
			close_btn = new JLabel(((ImageIcon) in("room/closeBtnOnExited")));
			add(title_lb, 0);
			add(comp_lb, 0);
			add(close_btn, 0);
		}

		@Override
		public void addCards(String cs) {
			if (!cs.equals("")) {
				String[] cd = cs.split(",");
				for (String id : cd)
					cards.add(new ItlCardLabel(Integer.parseInt(id), false));
				int t = cards.size();
				for (int i = 0; i < cards.size(); i++)
					cards.get(i).setLocation(sett.getImItlX(i, t, -1), sett.getImItlY());
				for (ItlCardLabel il : cards)
					add(il, 0);
			}
		}

	}

	class ClosePanelListener extends MouseAdapter {

		@Override
		public void mouseEntered(MouseEvent e) {
			if (seeItl_pn != null) {
				seeItl_pn.close_btn.setIcon(((ImageIcon) in("room/closeBtnOnEntered")));// GameImage.get("close_onEntered")));
				getAudio("�i");
			}
		}

		@Override
		public void mouseClicked(MouseEvent e) {
			if (!e.isMetaDown()) {
				JLabel lb = (JLabel) e.getSource();
				if (seeItl_pn.isAncestorOf(lb)) {
					c.remove(seeItl_pn);
					c.repaint();
					seeItl_pn = null;
				}
			}
		}

		@Override
		public void mouseExited(MouseEvent e) {
			if (seeItl_pn != null)
				seeItl_pn.close_btn.setIcon(((ImageIcon) in("room/closeBtnOnExited")));// GameImage.get("close_onExited")));
		}

	}

	class TrashLabel extends JLabel {

		public String mess;

		public TrashLabel(String text) {
			super(text, JLabel.CENTER);
			setOpaque(true);
			setVisible(true);
			setBackground(Color.YELLOW);
			switch (text) {
			case "��P��":
				setBounds(sett.getTrash());
				mess = "�D�зǰT��:�[�ݱ���:100";
				break;
			case "�P�w":
				setBounds(sett.getMt());
				mess = "�D�зǰT��:�[�ݱ���:101";
				break;
			}
			addMouseListener(ml);
		}
	}

	class IdealLabel extends MessageLabel {

		public double fontr = 0.8;
		boolean bold = false;

		public IdealLabel(String text, String mess) {
			super(text);
			setVisible(true);
			setVerticalAlignment(JLabel.CENTER);
			message = mess;
			setBounds(0, 0, 0, 0);
			setForeground(Color.WHITE);
			if (mess != null) {
				setOpaque(true);
				setBackground(Setting.onExited_col);
				addMouseListener(dl);
				setHorizontalAlignment(JLabel.CENTER);
			} else {
				setOpaque(false);
			}
		}

		@Override
		public void setBounds(int x, int y, int w, int h) {
			super.setBounds(x, y, w, h);
			if (bold)
				setFont(new Font("�L�n������", Font.BOLD, (int) (h * fontr)));
			else
				setFont(new Font("�L�n������", Font.PLAIN, (int) (h * fontr)));
		}

		@Override
		public void setBounds(Rectangle r) {
			super.setBounds(r);
			if (bold)
				setFont(new Font("�L�n������", Font.BOLD, (int) (r.height * fontr)));
			else
				setFont(new Font("�L�n������", Font.PLAIN, (int) (r.height * fontr)));
		}

	}

	class IdealListener extends MouseAdapter {

		@Override
		public void mouseEntered(MouseEvent e) {
			if ((((MessageLabel) (e.getSource())).message) != null) {
				((JLabel) e.getSource()).setBackground(Setting.onEntered_col);
				bo_au.play();
			}
		}

		@Override
		public void mouseClicked(MouseEvent e) {
			if (!e.isMetaDown()) {
				String mess = (((MessageLabel) (e.getSource())).message);

				if (mess != null) {
					if (mess.startsWith("!")) {
						mess = mess.substring(1);
						String[] ms = mess.split(":");
						switch (ms[0]) {
						case "login":
							String us = usnm_ta.getText(), ps = String.valueOf(pswd_ta.getPassword());
							if (us.equals("") && ps.equals(""))
								logErr_lb.setText("�A�S���f�f");
							else if (us.indexOf(" ") != -1)
								logErr_lb.setText("�b�����঳�Ů�");
							else if (us.equals(""))
								logErr_lb.setText("�A�ѤF���b��");
							else if (ps.equals(""))
								logErr_lb.setText("�A�S���K�X");
							else
								print("�n�D�n�J:" + us + ":" + ps);
							break;
						case "�y��":
							if (c.isAncestorOf(gamechatSound_pn)) {
								c.remove(gamechatSound_pn);
								c.repaint();
							} else {
								c.add(gamechatSound_pn, (Integer) 998);
								System.out.println("��ܤF");
								System.out.println(gamechatSound_pn);
							}
							break;
						case "�`�λy":
							c.remove(gamechatSound_pn);
							c.repaint();
							print("�D�зǰT��:�`�λy:" + ms[1]);
							break;
						default:
							new LogicException("���~��mess: " + mess).printStackTrace();
							break;
						}
					} else
						print(mess);
				}
			}
		}

		@Override
		public void mouseExited(MouseEvent e) {
			MessageLabel lb = (MessageLabel) e.getSource();
			if (lb.message != null) {
				lb.setBackground(Setting.onExited_col);
			}
		}
	}

	class IdealField extends JTextField {

		public IdealField() {
		}

		@Override
		public void setBounds(int x, int y, int w, int h) {
			super.setBounds(x, y, w, h);
			setFont(new Font("�L�n������", Font.PLAIN, (int) (h * 0.8)));
		}

		@Override
		public void setBounds(Rectangle r) {
			super.setBounds(r);
			setFont(new Font("�L�n������", Font.PLAIN, (int) (r.height * 0.8)));
		}

	}

	class IdealPassword extends JPasswordField {

		public IdealPassword() {
		}

		@Override
		public void setBounds(int x, int y, int w, int h) {
			super.setBounds(x, y, w, h);
			setFont(new Font("�L�n������", Font.PLAIN, (int) (h * 0.8)));
		}

		@Override
		public void setBounds(Rectangle r) {
			super.setBounds(r);
			setFont(new Font("�L�n������", Font.PLAIN, (int) (r.height * 0.8)));
		}

	}

	class Lobby {

		public ArrayList<RoomLabel> rooms = new ArrayList<>();

		public Lobby() {
			for (int i = 0; i < Setting.list - 1; i++)
				rooms.add(new RoomLabel(i));
		}
	}

	class RoomLabel extends MessageLabel {

		public IdealLabel id_lb, name_lb, count_lb, stus_lb;
		public int n;

		public RoomLabel(int n) {
			super();
			this.n = n;
			Rectangle b = sett.getRoom(n);
			int w = b.width;
			int h = b.height;
			int x = b.x;
			int y = b.y;
			setBounds(b);
			setVisible(true);
			setOpaque(true);
			setBackground(Color.LIGHT_GRAY);

			id_lb = new IdealLabel(String.valueOf(n), null);
			id_lb.setOpaque(false);
			id_lb.setHorizontalAlignment(JLabel.CENTER);
			id_lb.setBounds(x + sett.getNmlSpc(), y + sett.getNmlSpc(), Math.round(w * 0.1f), h - sett.getNmlSpc() * 2);
			name_lb = new IdealLabel("���J��", null);
			name_lb.setOpaque(false);
			name_lb.setHorizontalAlignment(JLabel.LEFT);
			name_lb.setBounds(x + Math.round(w * 0.20f), y + sett.getNmlSpc(), Math.round(w * 0.40f),
					h - sett.getNmlSpc() * 2);
			stus_lb = new IdealLabel("���J��", null);
			stus_lb.setOpaque(false);
			stus_lb.setHorizontalAlignment(JLabel.LEFT);
			stus_lb.setBounds(x + Math.round(w * 0.65f), y + sett.getNmlSpc(), Math.round(w * 0.20f),
					h - sett.getNmlSpc() * 2);
			count_lb = new IdealLabel("0 / 0", null);
			count_lb.setOpaque(false);
			count_lb.setHorizontalAlignment(JLabel.LEFT);
			count_lb.setBounds(x + w - Math.round(w * 0.1f) - sett.getNmlSpc(), y + sett.getNmlSpc(), Math.round(w * 0.1f),
					h - sett.getNmlSpc() * 2);
			message = null;
			addMouseListener(dl);
			show();
		}

		@Override
		public void show() {
			c.add(this, (Integer) 1);
			c.add(id_lb, (Integer) 2);
			c.add(name_lb, (Integer) 2);
			c.add(count_lb, (Integer) 2);
			c.add(stus_lb, (Integer) 2);
		}

		public void setStatus(int s) {
			switch (s) {
			case 0:
				stus_lb.setText("���ݤ�");
				message = "�i�J�ж�:" + n;
				setBackground(Setting.onExited_col);
				break;
			case 1:
				stus_lb.setText("�ж��w��");
				setBackground(Color.LIGHT_GRAY);
				message = null;
				break;
			case 2:
				stus_lb.setText("�C����");
				setBackground(Color.LIGHT_GRAY);
				message = null;
				break;
			default:
				new LogicException("st: " + s).printStackTrace();
				break;
			}
		}

	}

	class RoomChrPanel extends JPanel {

		class RoomChrLabel extends JLabel {

			String messageR, message;

			public RoomChrLabel(ImageIcon img) {
				super(img, JLabel.CENTER);
				messageR = "���a����:" + seat;
				addMouseListener(rl);
			}
		}

		RoomChrLabel chr_lb;
		JLabel ready_lb;
		IdealLabel nm_lb;
		int seat = -1;

		public RoomChrPanel(int s) {
			seat = s;
			setBounds(sett.getRoomChr(s));
			setVisible(true);
			setOpaque(false);
			setLayout(null);
			chr_lb = new RoomChrLabel((ImageIcon) in("character/����"));// GameImage.get("chr����"));
			chr_lb.setBounds(0, 0, sett.getChrLng(), sett.getChrLng());
			chr_lb.setVisible(true);
			chr_lb.setOpaque(false);
			nm_lb = new IdealLabel("", null);
			nm_lb.setOpaque(false);
			nm_lb.setHorizontalAlignment(JLabel.CENTER);
			nm_lb.setBounds(0, sett.getChrLng(), sett.getChrLng() + sett.getRoomChrSpc(), sett.getRoomChrNameHei());
			ready_lb = new JLabel();
			ready_lb.setVisible(true);
			ready_lb.setOpaque(false);
			ready_lb.setBounds(0, 0, sett.getChrLng(), sett.getChrLng());
			ready_lb.setHorizontalAlignment(JLabel.CENTER);
			ready_lb.setVerticalAlignment(JLabel.CENTER);

			add(chr_lb, 0);
			add(nm_lb, 0);
			add(ready_lb, 0);
		}

		public void setReady(boolean b) {
			if (b)
				ready_lb.setIcon((ImageIcon) in(("room/ready")));// GameImage.get("ready"));
			else
				ready_lb.setIcon(null);
		}

		public void setPlayer(boolean b, String name) {
			if (b) {
				if (name == null) {
					chr_lb.setIcon((ImageIcon) in("character/����"));// GameImage.get("chrNoLogo����"));
					setReady(false);
					nm_lb.setText(null);
				} else {
					chr_lb.setIcon(Tool.todaysChr());
					nm_lb.setText(name);
				}
			} else { // ����
				chr_lb.setIcon((ImageIcon) in("room/closed"));
				nm_lb.setText(null);
				setReady(false);
			}
		}
	}

	class Room {

		ArrayList<RoomChrPanel> roomChrs = new ArrayList<>();
		IdealLabel back_lb, ready_lb, id_lb;

		public Room(boolean isChief, int id) {
			for (int c = 0; c < 9; c++)
				roomChrs.add(new RoomChrPanel(c));
			back_lb = new IdealLabel("��^�j�U", "��^�j�U");
			back_lb.setBounds(sett.getRoomTxt(-2));
			ready_lb = new IdealLabel("�ǳ�", "�ǳ�");
			ready_lb.setBounds(sett.getRoomTxt(-1));
			setChief(isChief);
			id_lb = new IdealLabel(id + " ����", null);
			id_lb.setBounds(sett.getRoomTxt(0));
		}

		public void setChief(boolean isChief) {
			if (isChief) {
				ready_lb.setText("�C���}�l");
				ready_lb.message = "�C���}�l";
				for (RoomChrPanel rc : roomChrs)
					rc.chr_lb.messageR = "���a����:" + rc.seat;

			} else {
				ready_lb.setText("�ǳ�");
				ready_lb.message = "�ǳ�";
				for (RoomChrPanel rc : roomChrs)
					rc.chr_lb.messageR = null;
			}
		}

		public void setChief(int c) {
			for (RoomChrPanel rcp : roomChrs)
				if (rcp.ready_lb.getIcon() != null)
					if (rcp.ready_lb.getIcon().equals((ImageIcon) in("room/chief")))
						rcp.ready_lb.setIcon(null);
			roomChrs.get(c).ready_lb.setIcon((ImageIcon) in("room/chief"));
		}

		public void setReady(int p, boolean isReady) {
			roomChrs.get(p).setReady(isReady);
		}

		public void setPlayer(int p, boolean isBlocked, String name) {
			roomChrs.get(p).setPlayer(isBlocked, name);
		}

		public void show() {
			Component[] cmps = c.getComponents();
			if (c.isAncestorOf(chat_pn))
				for (Component cmp : cmps) {
					if (cmp != $c && cmp != chat_pn)
						c.remove(cmp);
				}
			else {
				for (Component cmp : cmps)
					if (cmp != $c)
						c.remove(cmp);
				c.add(chat_pn);
			}
			c.repaint();
			for (RoomChrPanel rc : roomChrs)
				c.add(rc);
			c.add(ready_lb);
			c.add(back_lb);
			c.add(id_lb);

		}
	}

	abstract class MessageLabel extends JLabel {
		String message;

		public MessageLabel(ImageIcon img, int pos) {
			super(img, pos);
		}

		public MessageLabel() {
			super();
		}

		public MessageLabel(String text) {
			super(text);
		}

	}

	class RoomChrLabelListener extends MouseAdapter {

		@Override
		public void mouseClicked(MouseEvent e) {
			RoomChrPanel.RoomChrLabel lb = (RoomChrPanel.RoomChrLabel) e.getSource();
			if (e.isMetaDown()) {
				if (lb.messageR != null)
					print(lb.messageR);
			} else {
				if (lb.message != null)
					print(lb.message);
			}
		}

	}

	class Move {

		int seat = -1; // 999��P 1000�P�w 300���� 301�����t½�} //1001�P�w�i��
		String loc = null;
		int cc = -1;
		// int same = 0;
		boolean shouldShine = true;

		public Move(int s, String l, int c) {
			seat = s;
			loc = l;
			cc = c;
		}

	}

	class Moveable {

		int sx, sy, sw, sh, mx, my, mw, mh, fx, fy, fw, fh, js0, js1, cc0, cc1;
		float dx, dy, dw, dh, ex, ey, ew, eh;
		JLabel lb0, lb1, lb;
		AudioClip au1, au2;
		HashMap<Integer, ImageIcon> hm;
		ArrayList<ImageIcon> ig;
		Move move0, move1;
		boolean hasSetMx = false;
		int id;
		Color col0, col1;

		@SuppressWarnings("unchecked")
		public Moveable(Move m0, Move m1, int id, String s1, String s2) {
			move0 = m0;
			move1 = m1;
			mh = sett.getFccHei();
			mw = sett.getFccWid();
			if (m0.seat == 1000 || m0.seat == 1001) {
				sw = sett.getItlFldWid();
				sh = sett.getItlHei();
				sx = mt_lb.getX() + (sett.getTrashLng() - sw) / 2;
				sy = mt_lb.getY() + (sett.getTrashLng() - sh) / 2;
				lb0 = mt_lb;
				col0 = Color.YELLOW;
			} else if (m0.seat == 999) {
				sw = sett.getTrashLng() / 2;
				sh = Math.round((float) sw * mh / mw);
				sx = trash_lb.getX() + (sett.getTrashLng() - sw) / 2;
				sy = trash_lb.getY() + (sett.getTrashLng() - sh) / 2;
				lb0 = trash_lb;
				col0 = Color.YELLOW;
			} else if (m0.seat == 300 || m0.seat == 301) {
				sw = sett.getItlWid();
				sh = sett.getItlHei();
				sx = itl_lb.getX();
				sy = itl_lb.getY();
			} else {
				sh = sett.getImHei();
				sw = Math.round((float) sh * mw / mh);
				sy = sett.getImY(m0.seat, game.plyCount);
				sx = sett.getImX(m0.seat, game.plyCount, m0.loc) + (sett.getImWid(m0.seat, game.plyCount, m0.loc) - sw) / 2;
				switch (m0.loc) {
				case "r":
					lb0 = game.im_r_lb.get(m0.seat);
					col0 = Setting.imRed;
					break;
				case "b":
					lb0 = game.im_b_lb.get(m0.seat);
					col0 = Setting.imBlue;
					break;
				case "k":
					lb0 = game.im_k_lb.get(m0.seat);
					col0 = Setting.imBlack;
					break;
				case "h":
					lb0 = game.im_h_lb.get(m0.seat);
					col0 = Setting.imHand;
					break;
				}

			}
			cc0 = m0.cc;
			if (m1.seat == 1000) {
				fw = sett.getTrashLng() / 2;
				fh = Math.round((float) fw * mh / mw);
				fx = mt_lb.getX() + (sett.getTrashLng() - fw) / 2;
				fy = mt_lb.getY() + (sett.getTrashLng() - fh) / 2;
				lb1 = mt_lb;
				col1 = Color.YELLOW;
			} else if (m1.seat == 999) {
				fw = sett.getTrashLng() / 2;
				fh = Math.round((float) fw * mh / mw);
				fx = trash_lb.getX() + (sett.getTrashLng() - fw) / 2;
				fy = trash_lb.getY() + (sett.getTrashLng() - fh) / 2;
				lb1 = trash_lb;
				col1 = Color.YELLOW;
			} else if (m1.seat == 300) {
				fw = sett.getItlWid();
				fh = sett.getItlHei();
				fx = itl_lb.getX();
				fy = itl_lb.getY();
			} else {
				fh = sett.getImHei();
				fw = Math.round((float) fh * mw / mh);
				fy = sett.getImY(m1.seat, game.plyCount);
				fx = sett.getImX(m1.seat, game.plyCount, m1.loc) + (sett.getImWid(m1.seat, game.plyCount, m1.loc) - fw) / 2;
				switch (m1.loc) {
				case "r":
					lb1 = game.im_r_lb.get(m1.seat);
					col1 = Setting.imRed;
					break;
				case "b":
					lb1 = game.im_b_lb.get(m1.seat);
					col1 = Setting.imBlue;
					break;
				case "k":
					lb1 = game.im_k_lb.get(m1.seat);
					col1 = Setting.imBlack;
					break;
				case "h":
					lb1 = game.im_h_lb.get(m1.seat);
					col1 = Setting.imHand;
					break;
				}
			}
			cc1 = m1.cc;
			mx = sx + (sw - mw) / 2;
			mx = Math.max(mx, Setting.nmlSpc);
			mx = Math.min(sett.scnHeight - Setting.nmlSpc - mw, mx);
			my = sy + (sh - mh) / 2;
			my = Math.max(my, Setting.nmlSpc);
			my = Math.min(sett.scnHeight - Setting.nmlSpc - 10 - mh, my);

			js0 = Setting.moveUpTime / Setting.anmSpc;
			js1 = Setting.moveDownTime / Setting.anmSpc;

			hm = (HashMap<Integer, ImageIcon>) in("gamecard/card_size" + Tool.toFileId(id));
			if (move0.seat == 301) {
				ig = (ArrayList<ImageIcon>) in("gamecard/getItlBy" + itl_lb.id + Tool.toFileId(id));
				lb = new JLabel(ig.get(0), JLabel.CENTER);
			} else if (move0.seat == 1001) {
				ig = (ArrayList<ImageIcon>) in("gamecard/openCardFromMt" + Tool.toFileId(id));
				lb = new JLabel(ig.get(0), JLabel.CENTER);
			} else {
				lb = new JLabel(hm.get(sh), JLabel.CENTER);
			}

			lb.setVisible(true);
			lb.setOpaque(false);
			lb.setBounds(sx, sy, sw, sh);
			if (!s1.equals("-1"))
				au1 = getAudio(s1);
			if (!s2.equals("-1"))
				au2 = getAudio(s2);
			this.id = id;
		}

		public void start() {
			dx = (float) (mx - sx) / js0;
			dy = (float) (my - sy) / js0;
			dw = (float) (mw - sw) / js0;
			dh = (float) (mh - sh) / js0;
			ex = (float) (fx - mx) / js1;
			ey = (float) (fy - my) / js1;
			ew = (float) (fw - mw) / js1;
			eh = (float) (fh - mh) / js1;
			if (move0.shouldShine && move0.seat != 300 && move0.seat != 301)
				shine(lb0, cc0, col0);
			c.add(lb, (Integer) 300);
			if (move0.seat == 300 || move0.seat == 301) {
				c.remove(itl_lb);
				c.repaint();
			}
		}

		public void setUp(int i) {
			float r = 1;
			if (move0.seat == 301 || move0.seat == 1001) {
				int h = sh + Math.round(i * dh * r);
				lb.setBounds(sx + Math.round(i * dx * r), sy + Math.round(i * dy * r), sw + Math.round(i * dw * r), h);
				lb.setIcon(ig.get(i));
			} else {
				int h = sh + Math.round(i * dh * r);
				lb.setBounds(sx + Math.round(i * dx * r), sy + Math.round(i * dy * r), sw + Math.round(i * dw * r), h);
				lb.setIcon(hm.get(h));
			}
		}

		public void setDown(int i) {
			int h = mh + Math.round(i * eh);
			lb.setBounds(mx + Math.round(i * ex), my + Math.round(i * ey), mw + Math.round(i * ew), h);
			lb.setIcon(hm.get(h));
		}

		public void finish() {
			c.remove(lb);
			repaint();
			if (move1.seat == 300) {
				itl_lb.setId(id);
				c.add(itl_lb, (Integer) 3);
			} else if (move1.shouldShine)
				shine(lb1, cc1, col1);
			if (au1 != null)
				au1.play();
			if (au2 != null)
				au2.play();
		}

		public void setMx(int mx0, int mx1, int n, int t) {
			int w = sett.getFccWid() * t + getSpc() * (t - 1);
			int m = (mx1 + sett.getFccWid() + mx0) / 2;
			int x0 = m - w / 2;
			x0 = Math.max(sett.getNmlSpc(), x0);
			x0 = Math.min(sett.scnWidth - w - sett.getNmlSpc(), x0);
			mx = x0 + (sett.getFccWid() + getSpc()) * n;
		}

		public int getSpc() {
			return (int) sett.rate;
		}

	}

	class SkillAnmPanel extends JPanel {

		JLabel chr_lb, txt_lb;
		JLabel[] lbs = new JLabel[Setting.skTxtCount];
		final int fh = sett.getSkillTxtFh(), sh = sett.getSkillTxtSh();
		int r, g, b;
		HashMap<Integer, ImageIcon> skimg;
		final float whrate = (float) sett.getSkillTxtF().width / sett.getSkillTxtF().height;
		String sound;
		int arr0, arr1;

		@SuppressWarnings("unchecked")
		public SkillAnmPanel(String chr, String sk, boolean red, String s, int arr0, int arr1) {

			Color col = red ? Setting.skRed1 : Setting.skBlue1;
			setBounds(sett.scnWidth, (sett.scnHeight - sett.getSkFldLng()) / 2, sett.getSkFldLng(), sett.getSkFldLng());
			setVisible(true);
			setOpaque(false);
			setLayout(null);
			skimg = (HashMap<Integer, ImageIcon>) in("skill/" + sk);
			chr_lb = new JLabel((ImageIcon) in("character/" + chr));
			txt_lb = new JLabel(skimg.get(sett.getSkillTxtF().height), JLabel.CENTER);

			chr_lb.setOpaque(false);
			chr_lb.setVisible(true);
			chr_lb.setBounds((sett.getSkFldLng() - sett.getChrLng()) / 2, (sett.getSkFldLng() - sett.getChrLng()) / 2,
					sett.getChrLng(), sett.getChrLng());
			txt_lb.setForeground(col);
			txt_lb.setBounds(sett.getSkillTxtF());
			add(chr_lb, 0);
			add(txt_lb, 0);
			for (int i = 0; i < Setting.skTxtCount; i++) {
				lbs[i] = new JLabel();
				lbs[i].setBounds(sett.getSkillTxtS());
				lbs[i].setHorizontalAlignment(JLabel.CENTER);
				lbs[i].setVerticalAlignment(JLabel.CENTER);
				lbs[i].setOpaque(false);
				lbs[i].setVisible(true);
				add(lbs[i], 0);
			}

			r = col.getRed();
			g = col.getGreen();
			b = col.getBlue();
			sound = s;
			this.arr0 = arr0;
			this.arr1 = arr1;
		}

		public void start() {
			c.add(this, (Integer) 300);
			int y = (sett.scnHeight - sett.getSkFldLng()) / 2;
			int js0 = Setting.skRunTime / Setting.anmSpc;
			int js1 = Setting.skMidTime / Setting.anmSpc;
			int js2 = js0;

			int sx0 = sett.scnWidth;
			int dx0 = Math.round((sett.scnWidth + sett.getSkFldLng()) * Setting.skRun);
			int sx1 = sx0 - dx0;
			int dx1 = Math.round((sett.scnWidth + sett.getSkFldLng()) * Setting.skMid);
			int sx2 = sx1 - dx1;
			int dx2 = dx0;

			getAudio("����/" + sound).play();
			for (int i = 0; i <= js0; i++) {
				setLocation(sx0 - Math.round((float) dx0 * i / js0), y);
				try {
					Thread.sleep(Setting.anmSpc);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
			if (arr0 != -1 && arr1 != -1)
				drawArrow(arr0, arr1);
			for (int i = 0; i <= js1; i++) {
				setLocation(sx1 - Math.round((float) dx1 * i / js1), y);
				for (int l = 0; l < Setting.skTxtCount; l++) {
					int lh = getH(l, i);
					if (lh != -1) {
						int w = Math.round(whrate * lh);
						lbs[l].setIcon(skimg.get(lh));
						lbs[l].setBounds((sett.getSkFldLng() - w) / 2, (sett.getSkFldLng() + sett.getChrLng()) / 2 - lh, w,
								lh);
					}
				}
				try {
					Thread.sleep(Setting.anmSpc);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
			for (int i = 0; i <= js2; i++) {
				setLocation(sx2 - Math.round((float) dx2 * i / js2), y);
				try {
					Thread.sleep(Setting.anmSpc);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}

			c.remove(this);
			c.repaint();
		}

		public int getH(int n, int t) {
			float j = (float) (Setting.skMidTime - Setting.skFloatTime) / Setting.skTxtCount / Setting.anmSpc;
			float d = (float) Setting.skFloatTime / Setting.anmSpc;
			int dh = sh - fh;
			float st = j * n;
			float ft = st + d;
			if (t < st || t > ft)
				return -1;
			else
				return fh + Math.round((t - st) / d * dh);
		}

		public Color getCol(int n, int t) {
			float j = (float) (Setting.skMidTime - Setting.skFloatTime) / Setting.skTxtCount / Setting.anmSpc;
			float d = (float) Setting.skFloatTime / Setting.anmSpc;
			float st = j * n;
			float ft = st + d;
			if (t < st || t > ft)
				return null;
			else
				return new Color(r, g, b, Math.round((ft - t) / d * 255));
		}

	}

	class SkillLabel extends JLabel {

		int skillId;
		boolean isRed;
		int status = 0; // 0���i�� //1�i�� //2��ܤ� //3�N��

		public SkillLabel(int id, int n, int t) {
			super(Skill.getSkillName(id), JLabel.CENTER);
			skillId = id;
			isRed = Skill.isRed(id);
			setBounds(sett.getSkill(n, t));
			setOpaque(true);
			setVisible(true);
			setBackground(Color.LIGHT_GRAY);
			setForeground(Color.WHITE);
			setFont(new Font("�L�n������", Font.PLAIN, Math.min((int) (24 * sett.rate), (int) (getHeight() * 0.4))));
			addMouseListener(skl);
			addMouseMotionListener(sml);
		}

		public void setStatus(int st) {
			status = st;
			switch (st) {
			case 0:
				setBackground(Color.LIGHT_GRAY);
				setText(Skill.getSkillName(skillId));
				setForeground(Color.WHITE);
				break;
			case 1:
				if (isRed)
					setBackground(Setting.skRed1);
				else
					setBackground(Setting.skBlue1);
				setText(Skill.getSkillName(skillId));
				setForeground(Color.WHITE);
				break;
			case 2:
				if (isRed)
					setBackground(Setting.skRed2);
				else
					setBackground(Setting.skBlue2);
				setText(Skill.getSkillName(skillId));
				setForeground(Color.WHITE);
				break;
			case 3:
				if (isRed) {
					setBackground(Setting.skRed3);
					setForeground(Setting.skRed1);
				} else {
					setBackground(Setting.skBlue3);
					setForeground(Setting.skBlue1);
				}
				setText("�� " + Skill.getSkillName(skillId) + " ��");
				break;
			default:
				new LogicException("���~��status: " + st).printStackTrace();
				break;
			}
		}

	}

	class SkillListener extends MouseAdapter {

		@Override
		public void mouseEntered(MouseEvent e) {
			SkillLabel lb = (SkillLabel) e.getSource();
			if (lb.status == 1) {
				if (lb.isRed)
					lb.setBackground(Setting.skRed2);
				else
					lb.setBackground(Setting.skBlue2);
				getAudio("�i");
			}
			skillComp_pn = new SkillComp(((SkillLabel) e.getSource()).skillId);
			skillComp_pn.setLocation(e.getXOnScreen(), e.getYOnScreen() - skillComp_pn.getHeight());
			c.add(skillComp_pn, (Integer) 1000);
		}

		@Override
		public void mouseClicked(MouseEvent e) {
			synchronized (sit) {
				SkillLabel lb = (SkillLabel) e.getSource();
				if (lb.status == 1) {
					print("�ϥΧޯ�:" + lb.skillId);
					lb.setStatus(2);
				}
			}
		}

		@Override
		public void mouseExited(MouseEvent e) {
			SkillLabel lb = (SkillLabel) e.getSource();
			if (lb.status == 1) {
				if (lb.isRed)
					lb.setBackground(Setting.skRed1);
				else
					lb.setBackground(Setting.skBlue1);
			}
			c.remove(skillComp_pn);
			c.repaint();
		}

	}

	class SkillMoveListener extends MouseMotionAdapter {

		@Override
		public void mouseMoved(MouseEvent e) {
			skillComp_pn.setLocation(e.getX() + ((JComponent) e.getSource()).getX() - 300 - 5,
					e.getY() + ((JComponent) e.getSource()).getY() - skillComp_pn.getHeight() - 5);
		}

	}

	class SkillComp extends JPanel {

		public int skillId;
		public JTextPane skillcomp;
		JLabel bg_lb;

		public SkillComp(int id) {
			skillId = id;
			skillcomp = new JTextPane();
			skillcomp.setVisible(true);
			skillcomp.setOpaque(false);
			skillcomp.setLocation(0, 0);
			skillcomp.setEditable(false);
			skillcomp.setSize(300, skillcomp.getPreferredSize().height);
			skillcomp.setDocument(Skill.getDoc(id, sett));
			skillcomp.setSize(300, skillcomp.getPreferredSize().height);
			bg_lb = new JLabel(Tool.black(skillcomp.getSize(), 255 / 2));
			bg_lb.setSize(skillcomp.getSize());
			bg_lb.setVisible(true);
			bg_lb.setLocation(0, 0);
			bg_lb.setOpaque(false);
			setOpaque(false);
			setVisible(true);
			setLayout(null);
			setSize(skillcomp.getSize());
			add(bg_lb, 0);
			add(skillcomp, 0);
		}

	}

	class NormalMoveListener extends MouseMotionAdapter {

		@Override
		public void mouseMoved(MouseEvent e) {
			JLabel lb = (JLabel) e.getSource();
			int x = e.getX() + lb.getX() + chooChr_pn.getX() + 5;
			if (x + chrComp_pn.getWidth() > sett.scnWidth - sett.getNmlSpc())
				x -= chrComp_pn.getWidth() - 10;
			int y = e.getY() + lb.getY() + chooChr_pn.getY() - chrComp_pn.getHeight() + 5;
			if (y < sett.getNmlSpc())
				y += chrComp_pn.getHeight() - 10;
			chrComp_pn.setLocation(x, y);
		}

	}

	class ChatPane extends JPanel {

		public JScrollPane scroll;
		public IdealField chat_ta;
		public JTextPane chatPane;
		public Doc chatDoc = new Doc(sett);;
		public static final int line = 2;
		public int column;

		public ChatPane() {
			super();
			setBounds(sett.getLobChat());
			setVisible(true);
			setOpaque(false);
			setLayout(null);
			setBorder(BorderFactory.createTitledBorder(BorderFactory.createLineBorder(Setting.onExited_col, 6), "��ѵ���",
					TitledBorder.LEFT, TitledBorder.TOP, new Font("�L�n������", Font.BOLD, sett.getLobListHei()), Color.WHITE));
			chatPane = new JTextPane();
			chatPane.setVisible(true);
			chatPane.setOpaque(false);
			chatPane.setDocument(chatDoc.get());
			scroll = new JScrollPane(chatPane, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
					JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
			scroll.setBounds(sett.getLobChatPad(), Math.round(sett.getLobChatPad() * 3.5f),
					sett.getLobChatWid() - sett.getLobChatPad() * 2,
					getHeight() - sett.getChatTaHei() - sett.getNmlSpc() * 2 - 2 - Math.round(sett.getLobChatPad() * 4.5f));
			scroll.getViewport().setOpaque(false);
			scroll.setOpaque(false);
			scroll.setBorder(BorderFactory.createEmptyBorder());
			JLabel lb = new JLabel();
			lb.setBounds(sett.getLobChatPad(),
					getHeight() - sett.getChatTaHei() - sett.getNmlSpc() - 2 - sett.getLobChatPad(),
					sett.getLobChatWid() - sett.getLobChatPad() * 2, 2);
			lb.setOpaque(true);
			lb.setVisible(true);
			lb.setBackground(Setting.skBlue1);
			chat_ta = new IdealField();
			chat_ta.setBounds(Math.round(getWidth() * Setting.lobChatPad),
					getHeight() - sett.getChatTaHei() - sett.getLobChatPad(),
					sett.getLobChatWid() - sett.getLobChatPad() * 2, sett.getChatTaHei());
			chat_ta.setOpaque(false);
			chat_ta.setBorder(BorderFactory.createLineBorder(Color.WHITE));
			chat_ta.setForeground(Color.WHITE);
			chat_ta.addKeyListener(ckl);
			chat_ta.setBorder(BorderFactory.createEmptyBorder());
			chat_ta.setCaretColor(Color.WHITE);
			column = scroll.getWidth()
					/ chatPane.getFontMetrics(new Font("�L�n������", Font.PLAIN, sett.getCompText())).stringWidth("1") - 5;
			add(scroll);
			add(lb);
			add(chat_ta);

		}

		public void speak(String username, String speaking) {
			chatDoc.add(5, username + "�@");
			chatDoc.add(0, setChatString(speaking) + "\n");
			chatPane.setDocument(chatDoc.get());
			chatPane.setCaretPosition(chatPane.getDocument().getLength());
		}

		public void warn(String speaking) {
			chatDoc.add(6, speaking + "\n");
			chatPane.setDocument(chatDoc.get());
			chatPane.setCaretPosition(chatPane.getDocument().getLength());
		}

		public String setChatString(String str) {
			StringBuilder sb = new StringBuilder(str);
			for (int i = 0; i < sb.length() - column; i++)
				if (isAllHalfWidth(sb.substring(i, i + column)))
					sb.insert(i + column, ' ');
			return sb.toString();
		}

		public boolean isAllHalfWidth(String str) {
			if (str.indexOf(" ") != -1)
				return false;
			for (int i = 0; i < str.length(); i++)
				if (!isHalfWidth(str.charAt(i)))
					return false;
			return true;
		}

		public boolean isHalfWidth(char character) {
			if (!('\u0000' <= character && character <= '\u00FF' || '\uFF61' <= character && character <= '\uFFDC'
					|| '\uFFE8' <= character && character <= '\uFFEE'))
				return false;
			return Character.UnicodeBlock.of(character) == Character.UnicodeBlock.BASIC_LATIN;
		}

	}

	class ChatKeyListener extends KeyAdapter {
		@Override
		public void keyPressed(KeyEvent e) {
			IdealField fd = (IdealField) e.getSource();
			if (e.getKeyCode() == KeyEvent.VK_ENTER && fd.isFocusOwner()) {
				if (fd == chat_pn.chat_ta) {
					String str = chat_pn.chat_ta.getText().trim();
					if (!str.equals("")) {
						str.replaceAll("#", "##");
						str.replaceAll("%", "%%");
						str.replaceAll(":", "%#");
						print("�j�U���:" + str);
						chat_pn.chat_ta.setText("");
					}
				} else if (fd == gamechat_ta) {
					String str = gamechat_ta.getText().trim();
					if (!str.equals("")) {
						str.replaceAll("#", "##");
						str.replaceAll("%", "%%");
						str.replaceAll(":", "%#");
						print("�D�зǰT��:�C�����:" + str);
						gamechat_ta.setText("");
					}
				}
			}
		}
	}

	class DarkLabel extends JLabel {

		public DarkLabel(int w, int h) {
			setVisible(true);
			setOpaque(false);
			setBounds(0, 0, w, h);
		}
	}

	class SpeakingLabel extends IdealLabel {

		public SpeakingLabel(String txt) {
			super(txt, "!�`�λy:" + txt);
			setHorizontalAlignment(JLabel.LEFT);
			setSize(sett.getSpeak());
			setBorder(BorderFactory.createLineBorder(Color.BLACK, 1));
		}

	}

	public static AudioClip getAudio(String filename) {
		return GetSource.getAudio("mic/" + filename + ".wav");
	}

}