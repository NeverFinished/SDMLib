package org.sdmlib.test.models.objects;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

import de.uniks.networkparser.IdMap;

public class DiffCreater {
	public static final String CRLF = "\r\n";

//	public static void create(IdMap map, ClassModel model, Object object) {
//		IdMap diff = new IdMap(map);
//
//		GraphList clazzModel = new Javascript().convertModelToGraphList(model);
//
//		GraphList highlightModel = diff.highlightModel(clazzModel,
//				diff.parsingObject(object));
//
//		GraphConverter converter = new GraphConverter();
//		try {
//			writeJson("se.html", converter.convert(highlightModel, true));
//		} catch (IOException e) {
//			e.printStackTrace();
//		}
//	}

	private static void writeJson(String fileName, String item) throws IOException {
		StringBuilder sb = new StringBuilder();
		sb.append("<html><head>" + CRLF);
		String path = IdMap.class.getResource("graph/graph.js").toString();
		path = path.substring(0, path.length() - 8);
		sb.append("\t<link rel=\"stylesheet\" type=\"text/css\" href=\"" + path
				+ "diagramstyle.css\">" + CRLF);
		sb.append("\t<script src=\"" + path + "graph.js\"></script>" + CRLF);
		sb.append("\t<script src=\"" + path + "dagre.min.js\"></script>" + CRLF);
		sb.append("\t<script src=\"" + path + "drawer.js\"></script>" + CRLF);
		sb.append("</head><body>" + CRLF);
		sb.append("<script language=\"Javascript\">" + CRLF);
		sb.append("\tvar json=" + item + ";" + CRLF);
		sb.append("\tnew Graph(json).layout();" + CRLF);
		sb.append("</script></body></html>");

		FileWriter fstream = new FileWriter("build/" + fileName);
		BufferedWriter out = new BufferedWriter(fstream);
		out.write(sb.toString());
		// Close the output stream
		out.close();
	}
}
