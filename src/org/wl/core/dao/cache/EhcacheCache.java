package org.wl.core.dao.cache;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

import javax.annotation.Resource;

import net.sf.ehcache.CacheException;
import net.sf.ehcache.CacheManager;
import net.sf.ehcache.Ehcache;
import net.sf.ehcache.Element;

import org.apache.ibatis.cache.Cache;
import org.springframework.beans.factory.InitializingBean;

public final class EhcacheCache implements Cache ,InitializingBean{

	@Resource(name="ehcache")
	private CacheManager cacheManager ;
	
	private static List<EhcacheCache> lazys = new ArrayList<EhcacheCache>();

	private final ReadWriteLock readWriteLock = new ReentrantReadWriteLock();
	private final String id;

	public void afterPropertiesSet() throws Exception {
		for (EhcacheCache ehcacheCache : lazys){
			if(this.cacheManager==null && !this.cacheManager.cacheExists(ehcacheCache.getId())){
				Ehcache cache = this.cacheManager.getCache(ehcacheCache.getId());
				if(cache==null)
					this.cacheManager.addCache(ehcacheCache.getId());
				ehcacheCache.cacheManager = this.cacheManager;
			}
		}
	}

	public EhcacheCache(String id) {
		if (id == null) {
			throw new IllegalArgumentException("Cache instances require an ID");
		}
		this.id = id;
		if (this.cacheManager==null || !this.cacheManager.cacheExists(this.id)){
			lazys.add(this);
		}
	}

	public void clear() {
		getCache().removeAll();
	}

	public String getId() {
		return this.id;
	}

	public Object getObject(Object key) {
		try {
			Element cachedElement = getCache().get(Integer.valueOf(key.hashCode()));
			if (cachedElement == null) {
				return null;
			}
			return cachedElement.getObjectValue();
		} catch (Throwable t) {
			throw new CacheException(t);
		}

	}

	public ReadWriteLock getReadWriteLock() {
		return this.readWriteLock;
	}

	public int getSize() {
		try {
			return getCache().getSize();
		} catch (Throwable t) {
			throw new CacheException(t);
		}

	}

	public void putObject(Object key, Object value) {
		try {
			getCache().put(new Element(Integer.valueOf(key.hashCode()), value));
		} catch (Throwable t) {
			throw new CacheException(t);
		}
	}

	public Object removeObject(Object key) {
		try {
			Object obj = getObject(key);
			getCache().remove(Integer.valueOf(key.hashCode()));
			return obj;
		} catch (Throwable t) {
			throw new CacheException(t);
		}
	}

	private Ehcache getCache() {
		return this.cacheManager.getCache(this.id);
	}

	public String toString() {
		return "EHCache {" + this.id + "}";
	}

	
}