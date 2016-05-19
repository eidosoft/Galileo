package com.eido.galileo.types;

import org.simpleframework.xml.Attribute;
import org.simpleframework.xml.Root;

/**
 * Created by Stefano on 28/12/15.
 */
@Root
public class Content {

    @Attribute
    protected String DescrContent;

    @Attribute
    protected ContentType Type;

    @Attribute
    protected String ContentURL;

    public ContentType getType() {
        return Type;
    }

    public String getContentURL() {
        return ContentURL;

    }

    public String getDescrContent() {
        return DescrContent;
    }
}
