package com.giffing.wicket.spring.boot.starter.configuration.extensions.wicketstuff.serializer.kryo2;

import org.apache.wicket.protocol.http.WebApplication;
import org.apache.wicket.serialize.ISerializer;
import org.apache.wicket.serialize.java.JavaSerializer;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.wicketstuff.pageserializer.kryo2.KryoSerializer;

import com.giffing.wicket.spring.boot.context.exceptions.extensions.ExtensionMisconfigurationException;
import com.giffing.wicket.spring.boot.context.extensions.ApplicationInitExtension;
import com.giffing.wicket.spring.boot.context.extensions.WicketApplicationInitConfiguration;

@ApplicationInitExtension
@ConditionalOnProperty(prefix = WicketSerializerKryo2Properties.PROPERTY_PREFIX, value = "enabled", matchIfMissing = true)
@ConditionalOnClass(value = org.wicketstuff.pageserializer.kryo2.KryoSerializer.class)
@EnableConfigurationProperties({ WicketSerializerKryo2Properties.class })
public class WicketSerializerKryo2Config implements WicketApplicationInitConfiguration {

	@Override
	public void init(WebApplication webApplication) {
		ISerializer currentSerializer = webApplication.getFrameworkSettings().getSerializer();
		if (currentSerializer.getClass().equals(JavaSerializer.class)) {
			webApplication.getFrameworkSettings().setSerializer(new KryoSerializer());
		} else {
			throw new ExtensionMisconfigurationException("Kryo2Config: There is already another serializer configured " + currentSerializer);
		}
	}

}
