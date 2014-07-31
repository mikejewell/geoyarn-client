package uk.ac.soton.ecs.geoyarn;

//Class copied from UBhave Framework for loading JSON 
/* *****************************************************************************
 *
 * UBhave Dynamic App Framework
 *

This application was developed as part of the EPSRC UBhave (Ubiquitous and
Social Computing for Positive Behaviour Change) Project. For more
information, please visit http://www.ubhave.org.

Copyright (c) 2014
  University of Southampton
    Charlie Hargood, cah07r.soton.ac.uk
    Danius Michaelides, dtm.soton.ac.uk
  University of Birmingham
    Veljko Pejovic, v.pejovic.bham.ac.uk
  University of Cambridge
    Neal Lathia, neal.lathia.cam.ac.uk
    Kiran Rachuri, kiran.rachuri.cam.ac.uk
All rights reserved.

Redistribution and use in source and binary forms, with or without
modification, are permitted provided that the following conditions are met:
    * Redistributions of source code must retain the above copyright
      notice, this list of conditions and the following disclaimer.
    * Redistributions in binary form must reproduce the above copyright
      notice, this list of conditions and the following disclaimer in the
      documentation and/or other materials provided with the distribution.
    * Neither the names of the Universities of Southampton, Birmingham and
      Cambridge nor the names of its contributors may be used to endorse or
      promote products derived from this software without specific prior
      written permission.

THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS"
AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE
IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE
ARE DISCLAIMED. IN NO EVENT SHALL THE ABOVE COPYRIGHT HOLDERS BE LIABLE FOR ANY
DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES
(INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES;
LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND
ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT
(INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF
THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.

***************************************************************************** */



import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;

import org.json.JSONArray;
import org.json.JSONObject;

public abstract class JSONLoader
{
	
	// TODO: Consider taking out, this was useful for demo only.	
	public static String loadFileContents(int file) throws IOException
	{
		try
		{
			StringBuffer fileContents = new StringBuffer();
			String line;
			InputStreamReader inputStream = new InputStreamReader(GeoyarnClientApplication.getContext().getResources().openRawResource(file));
			BufferedReader in = new BufferedReader(inputStream);
			while ((line = in.readLine()) != null)
			{
				fileContents.append(line);
			}
			in.close();
			return fileContents.toString();
		}
		catch (FileNotFoundException e)
		{
			e.printStackTrace();
			return null;
		}
	}
	
	public static String loadFileContents(String filename) throws IOException
	{
		StringBuffer fileContents = new StringBuffer();
		BufferedReader in = new BufferedReader(new InputStreamReader(GeoyarnClientApplication.getContext().openFileInput(filename)));
		String line;
		while ((line = in.readLine()) != null)
		{
			fileContents.append(line);
		}
		in.close();
		
		return fileContents.toString();
	}
	
	
	public static JSONObject getFileContents(final String fileName) throws Exception
	{
		StringBuffer fileContents = new StringBuffer();
		BufferedReader in = new BufferedReader(new InputStreamReader(GeoyarnClientApplication.getContext().openFileInput(fileName)));
		String line;
		while ((line = in.readLine()) != null)
		{
			fileContents.append(line);
		}
		in.close();
		return new JSONObject(fileContents.toString());
	}

	protected static JSONArray getJSONArrayFromFile(int file, String key)
	{
		try
		{
			String fileContents = loadFileContents(file);
			JSONObject data = new JSONObject(fileContents);
			return data.getJSONArray(key);
		}
		catch (Exception e)
		{
			e.printStackTrace();
			return null;
		}
	}

}
