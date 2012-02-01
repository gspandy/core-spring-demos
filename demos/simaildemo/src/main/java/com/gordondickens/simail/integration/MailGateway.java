package com.gordondickens.simail.integration;

import com.gordondickens.simail.domain.Recipient;
import org.springframework.integration.annotation.Gateway;

/**
 * This class extends FileTypeMap and provides data typing of files via their
 * file extension. It uses the <code>.mime.types</code> format.
 * <p/>
 * <p/>
 * <b>MIME types file search order:</b>
 * <p/>
 * The MimetypesFileTypeMap looks in various places in the user's system for
 * MIME types file entries. When requests are made to search for MIME types in
 * the MimetypesFileTypeMap, it searches MIME types files in the following
 * order:
 * <p/>
 * <ol>
 * <li>Programmatically added entries to the MimetypesFileTypeMap instance.
 * <li>The file <code>.mime.types</code> in the user's home directory.
 * <li>The file &lt;<i>java.home</i>&gt;<code>/lib/mime.types</code>.
 * <li>The file or resources named <code>META-INF/mime.types</code>.
 * <li>The file or resource named <code>META-INF/mimetypes.default</code>
 * (usually found only in the <code>activation.jar</code> file).
 * </ol>
 * <p/>
 * <b>MIME types file format:</b>
 * <p/>
 * <p/>
 * <code>
 * # comments begin with a '#'<br>
 * # the format is &lt;mime type> &lt;space separated file extensions><br>
 * # for example:<br>
 * text/plain    txt text TXT<br>
 * # this would map file.txt, file.text, and file.TXT to<br>
 * # the mime type "text/plain"<br>
 * </code>
 */
public interface MailGateway {
    @Gateway
    public void sendMail(Recipient recipient);
}
