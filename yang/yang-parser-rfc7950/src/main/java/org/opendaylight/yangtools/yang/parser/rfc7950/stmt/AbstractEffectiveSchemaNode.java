/*
 * Copyright (c) 2015 Cisco Systems, Inc. and others.  All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0 which accompanies this distribution,
 * and is available at http://www.eclipse.org/legal/epl-v10.html
 */
package org.opendaylight.yangtools.yang.parser.rfc7950.stmt;

import com.google.common.collect.ImmutableList;
import java.util.List;
import javax.annotation.Nonnull;
import org.opendaylight.yangtools.yang.common.QName;
import org.opendaylight.yangtools.yang.model.api.SchemaNode;
import org.opendaylight.yangtools.yang.model.api.SchemaPath;
import org.opendaylight.yangtools.yang.model.api.UnknownSchemaNode;
import org.opendaylight.yangtools.yang.model.api.meta.DeclaredStatement;
import org.opendaylight.yangtools.yang.model.api.meta.EffectiveStatement;
import org.opendaylight.yangtools.yang.parser.spi.meta.StmtContext;

public abstract class AbstractEffectiveSchemaNode<D extends DeclaredStatement<QName>> extends
        AbstractSchemaEffectiveDocumentedNode<QName, D> implements SchemaNode {

    private final SchemaPath path;
    private final List<UnknownSchemaNode> unknownNodes;

    protected AbstractEffectiveSchemaNode(final StmtContext<QName, D, ?> ctx) {
        super(ctx);
        this.path = ctx.getSchemaPath().get();

        ImmutableList.Builder<UnknownSchemaNode> listBuilder = new ImmutableList.Builder<>();
        for (EffectiveStatement<?, ?> effectiveStatement : effectiveSubstatements()) {
            if (effectiveStatement instanceof UnknownSchemaNode) {
                listBuilder.add((UnknownSchemaNode) effectiveStatement);
            }
        }
        this.unknownNodes = listBuilder.build();
    }

    @Nonnull
    @Override
    public final QName getQName() {
        return path.getLastComponent();
    }

    @Nonnull
    @Override
    public final SchemaPath getPath() {
        return path;
    }

    @Nonnull
    @Override
    public final List<UnknownSchemaNode> getUnknownSchemaNodes() {
        return unknownNodes;
    }
}
