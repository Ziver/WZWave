/*
 *******************************************************************************
 * Copyright (c) 2016 Whizzo Software, LLC.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *******************************************************************************
*/
package com.whizzosoftware.wzwave.persist;

import com.whizzosoftware.wzwave.node.NodeCreationException;
import com.whizzosoftware.wzwave.node.NodeListener;
import com.whizzosoftware.wzwave.node.ZWaveNode;
import com.whizzosoftware.wzwave.node.ZWaveNodeFactory;
import com.whizzosoftware.wzwave.persist.PersistenceContext;
import com.whizzosoftware.wzwave.persist.PersistentStore;
import com.whizzosoftware.wzwave.persist.mapdb.MapDbPersistenceContext;
import org.mapdb.DB;
import org.mapdb.DBMaker;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

/**
 * A HashMap implementation of PersistentStore.
 *
 * @author Ziver Koc
 */
public class HashMapPersistentStore implements PersistentStore, PersistenceContext {

    private HashMap<String, HashMap<String,Object>> db;


    public HashMapPersistentStore() {
        db = new HashMap<>();
    }


    @Override
    public ZWaveNode getNode(byte nodeId, NodeListener listener) throws NodeCreationException {
        return ZWaveNodeFactory.createNode(this, nodeId, listener);
    }

    @Override
    public void saveNode(ZWaveNode node) {
        node.save(this);
    }


    @Override
    public Map<String, Object> getNodeMap(int nodeId) {
        return get("" + nodeId);
    }

    @Override
    public Map<String, Object> getCommandClassMap(int nodeId, int commandClassId) {
        return get(nodeId + "." + commandClassId);
    }

    private Map<String, Object> get(String key) {
        if (!db.containsKey(key))
            db.put(key, new HashMap<String, Object>());
        return db.get(key);
    }

}
