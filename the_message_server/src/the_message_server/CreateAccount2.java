package the_message_server;

import java.io.*;
import java.util.ArrayList;

public class CreateAccount2 {


	public static void main(String[] args) {

		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		String readed = null;
		try {
			while ((readed = br.readLine()) != null) {
				String[] read = readed.split(" ");
				if (read.length == 1) {
					switch (read[0]) {
					case "����":
						System.out.println("�[�s�b�� �b�� �K�X");
						System.out.println("�d�߱b����T �b���W��");
						System.out.println("�d��UID��T UID");
						System.out.println("�ˬdUID�O�_����");
						break;
					case "�ˬdUID�O�_����":
						ArrayList<Integer> UIDs = new ArrayList<>();
						File docu = new File("players");
						String[] filenames;
						String fullpath = docu.getAbsolutePath();
						filenames = docu.list();
						for (int i = 0; i < filenames.length; i++) {
							File tempFile = new File(fullpath + "\\" + filenames[i]);
							if (!tempFile.isDirectory())
								if (filenames[i].endsWith(".dll") && !filenames[i].equals("UID.dll")) {
									ObjectInputStream ois = new ObjectInputStream(
											new FileInputStream("players/" + filenames[i]));
									try {
										UserFile uf = (UserFile) ois.readObject();
										System.out.println(uf.username + " " + uf.password + " " + uf.UID);
										int u = uf.UID;
										if (UIDs.contains((Integer) u))
											System.out.println("�ˬd�쭫�Ъ�UID: " + u);
										else
											UIDs.add(u);
									} catch (ClassNotFoundException e) {
										e.printStackTrace();
									}
									ois.close();
								}
						}
						System.out.println("�ˬd����");
						break;
					default:
						System.out.println("�����T�����O");
						System.out.println("�[�s�b�� �b�� �K�X");
						System.out.println("�d�߱b����T �b���W��");
						System.out.println("�d��UID��T UID");
						System.out.println("�ˬdUID�O�_����");
						break;
					}
				} else if (read.length == 2) {
					switch (read[0]) {
					case "�d�߱b����T":
						String username = read[1];
						try {
							ObjectInputStream ois = new ObjectInputStream(
									new FileInputStream("players/" + username + ".dll"));
							UserFile uf = (UserFile) ois.readObject();
							System.out.println(uf.username + " " + uf.password + " " + uf.UID);
						} catch (ClassNotFoundException e) {
							e.printStackTrace();
						} catch (FileNotFoundException e) {
							System.out.println("�b�����s�b");
						}
						break;
					case "�ˬdUID��T":
						break;
					default:
						System.out.println("�����T�����O");
						System.out.println("�[�s�b�� �b�� �K�X");
						System.out.println("�d�߱b����T �b���W��");
						System.out.println("�d��UID��T UID");
						System.out.println("�ˬdUID�O�_����");
						break;
					}
				} else if (read.length == 3) {
					switch (read[0]) {
					case "�[�s�b��":
						UserFile user = new UserFile();
						user.username = read[1];
						user.password = read[2];

						File file = new File("players/" + user.username + ".dll");
						if (file.exists()) {
							System.out.println("�b���w�s�b");
							break;
						}
						int uid = 0;
						try {
							ObjectInputStream ois = new ObjectInputStream(new FileInputStream("players/UID.dll"));
							uid = (Integer) ois.readObject();
							if (uid == 0) {
								System.out.println("Ū��UID����");
								return;
							} else if (uid < 100) {
								uid = 99;
							}
							uid++;
						} catch (ClassNotFoundException e) {
							e.printStackTrace();
						} catch (FileNotFoundException e) {
							uid = 100;
						}
						user.UID = uid;
						ObjectOutputStream oos = new ObjectOutputStream(
								new FileOutputStream("players/" + user.username + ".dll"));
						oos.writeObject(user);
						oos.close();
						oos = new ObjectOutputStream(new FileOutputStream("players/UID.dll"));
						oos.writeObject(uid);
						System.out.println("�w�[�J�b��: " + user.username + " " + user.password + " " + user.UID);
						oos.close();
						break;
					case "�[�s���ձb��":
						user = new UserFile();
						user.username = read[1];
						user.password = read[2];

						file = new File("players/" + user.username + ".dll");
						if (file.exists()) {
							System.out.println("�b���w�s�b");
							break;
						}
						uid = 0;
						try {
							ObjectInputStream ois = new ObjectInputStream(new FileInputStream("players/UID.dll"));
							uid = (Integer) ois.readObject();
							uid++;
						} catch (ClassNotFoundException e) {
							e.printStackTrace();
						} catch (FileNotFoundException e) {
							uid = 0;
						}
						user.UID = uid;
						oos = new ObjectOutputStream(new FileOutputStream("players/" + user.username + ".dll"));
						oos.writeObject(user);
						oos.close();
						oos = new ObjectOutputStream(new FileOutputStream("players/UID.dll"));
						oos.writeObject(uid);
						System.out.println("�w�[�J�b��: " + user.username + " " + user.password + " " + user.UID);
						oos.close();
						break;
					}
				} else if (read.length == 4) {
					if (read[0].equals("�[�s�b��")) {
						UserFile user = new UserFile();
						user.username = read[1];
						user.password = read[2];
						int uid;
						try {
							uid = Integer.parseInt(read[3]);
							System.out.println("UID��" + uid);
						} catch (NumberFormatException e) {
							System.out.println("���~��UID");
							break;
						}
						boolean chungfu = false;
						File docu = new File("players");
						String[] filenames;
						String fullpath = docu.getAbsolutePath();
						filenames = docu.list();
						for (int i = 0; i < filenames.length; i++) {
							File tempFile = new File(fullpath + "\\" + filenames[i]);
							if (!tempFile.isDirectory())
								if (filenames[i].endsWith(".dll") && !filenames[i].equals("UID.dll")) {
									ObjectInputStream ois = new ObjectInputStream(
											new FileInputStream("players/" + filenames[i]));
									try {
										UserFile uf = (UserFile) ois.readObject();
										int u = uf.UID;
										if (u == uid) {
											System.out.println("���ƪ�UID");
											chungfu = true;
											break;
										}
									} catch (ClassNotFoundException e) {
										e.printStackTrace();
									}
									ois.close();
								}
						}
						if (chungfu)
							break;
						user.UID = uid;
						ObjectOutputStream oos = new ObjectOutputStream(
								new FileOutputStream("players/" + user.username + ".dll"));
						oos.writeObject(user);
						oos.close();
						System.out.println("�w�[�J�b��: " + user.username + " " + user.password + " " + user.UID);
						oos.close();
					}
				}
			}
		} catch (IOException e2) {
			e2.printStackTrace();
		}

		/*
		 * if (true) { int uid = 103;
		 * 
		 * try (ObjectInputStream ois = new ObjectInputStream(new
		 * FileInputStream("players/UID.dll"))) { uid = (Integer)
		 * ois.readObject(); System.out.println("��oUID��" + uid); } catch
		 * (FileNotFoundException e1) { e1.printStackTrace(); } catch
		 * (IOException e1) { e1.printStackTrace(); } catch
		 * (ClassNotFoundException e) { e.printStackTrace(); }
		 * 
		 * uid++; System.out.println("�sUID��" + uid);
		 * 
		 * try (ObjectOutputStream oos = new ObjectOutputStream(new
		 * FileOutputStream("players/" + username + ".dll"))) { UserFile pl1 =
		 * new UserFile(); pl1.password = password; pl1.UID = uid; pl1.username
		 * = username; oos.writeObject(pl1); } catch (FileNotFoundException e) {
		 * e.printStackTrace(); } catch (IOException e) { e.printStackTrace(); }
		 * 
		 * try (ObjectOutputStream oos = new ObjectOutputStream(new
		 * FileOutputStream("players/UID.dll"))) { oos.writeObject(uid); } catch
		 * (FileNotFoundException e) { e.printStackTrace(); } catch (IOException
		 * e) { e.printStackTrace(); } } else {
		 * 
		 * int uid = 103;
		 * 
		 * try (ObjectOutputStream oos = new ObjectOutputStream(new
		 * FileOutputStream("players/UID.dll"))) { oos.writeObject(uid); } catch
		 * (FileNotFoundException e) { e.printStackTrace(); } catch (IOException
		 * e) { e.printStackTrace(); } }
		 */
	}

}
