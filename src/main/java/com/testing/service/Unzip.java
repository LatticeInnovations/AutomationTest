package com.testing.service;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Scanner;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

import org.springframework.stereotype.Service;

@Service
public class Unzip {

	public void unzip() throws Exception {

		Scanner sc = new Scanner(System.in);
		String[] args = new String[2];
		System.out.println("enter source path");
		args[0] = sc.nextLine();
		System.out.println("enter destination path");
		args[1] = sc.nextLine();

		System.out.println("entered");

		if (args.length < 2) {
			throw new Exception(
					"Insufficient no of parameters. Required-Parameter1: Full zip file name, Parameter2: Dest dir");

		}
		String zipFileName = args[0];
		File destDirectory = new File(args[1]);
		byte[] buffer = new byte[1024];
		ZipInputStream zis = new ZipInputStream(new FileInputStream(zipFileName));
		ZipEntry zipEntry = zis.getNextEntry();
		while (zipEntry != null) {
			File newFile = newFile(destDirectory, zipEntry);
			if (zipEntry.isDirectory()) {
				if (!newFile.isDirectory() && !newFile.mkdirs()) {
					throw new IOException("Failed to create directory " + newFile);
				}
			} else {
				// fix for Windows-created archives
				File parent = newFile.getParentFile();
				if (!parent.isDirectory() && !parent.mkdirs()) {
					throw new IOException("Failed to create directory " + parent);
				}

				// write file content
				FileOutputStream fos = new FileOutputStream(newFile);
				int len;
				while ((len = zis.read(buffer)) > 0) {
					fos.write(buffer, 0, len);
				}
				fos.close();
			}
			zipEntry = zis.getNextEntry();
		}
		zis.closeEntry();
		zis.close();
		System.out.println("Unzipping complete");

		int ind = zipFileName.lastIndexOf("\\");
		String path = zipFileName.substring(0, ind + 1);
		int dot_ind = zipFileName.indexOf('.');
		String fname = zipFileName.substring(ind + 1, dot_ind);
		String ext = zipFileName.substring(dot_ind + 1);

//		System.out.println("index number " + ind);
//		System.out.println("Path " + path);
//		System.out.println("File name " + fname);
//		System.out.println("Extension " + ext);

		String[] command = { "cmd", };
		Process p;
		try {
			p = Runtime.getRuntime().exec(command);
			new Thread(new SyncPipe(p.getErrorStream(), System.err)).start();
			new Thread(new SyncPipe(p.getInputStream(), System.out)).start();
			PrintWriter stdin = new PrintWriter(p.getOutputStream());

			stdin.println("cd " + destDirectory+ "\\" + fname);
			stdin.println("mvn install");
			stdin.println("java -jar target\\" + "sample" + "-0.0.1-SNAPSHOT.jar");
			
			stdin.close();
			p.waitFor();
		} catch (Exception e) {
			e.printStackTrace();
		}

	}
	
	public static File newFile(File destinationDir, ZipEntry zipEntry) throws IOException {
	    File destFile = new File(destinationDir, zipEntry.getName());

	    String destDirPath = destinationDir.getCanonicalPath();
	    String destFilePath = destFile.getCanonicalPath();

	    if (!destFilePath.startsWith(destDirPath + File.separator)) {
	        throw new IOException("Entry is outside of the target dir: " + zipEntry.getName());
	    }

	    return destFile;
	}
}
