
CREATE TABLE inventory (
           id BIGINT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
           variant_id BIGINT NOT NULL UNIQUE,
           on_hand_quantity INTEGER NOT NULL DEFAULT 0,
           reserved_quantity INTEGER NOT NULL DEFAULT 0,
           reorder_level INTEGER NOT NULL DEFAULT 10,
           created_at TIMESTAMPTZ NOT NULL DEFAULT CURRENT_TIMESTAMP,
           updated_at TIMESTAMPTZ NOT NULL DEFAULT CURRENT_TIMESTAMP,

           CONSTRAINT fk_inventory_variant
               FOREIGN KEY (variant_id)
                   REFERENCES product_variants (id),

           CONSTRAINT chk_inventory_on_hand_non_negative
               CHECK (on_hand_quantity >= 0),

           CONSTRAINT chk_inventory_reserved_non_negative
               CHECK (reserved_quantity >= 0),

           CONSTRAINT chk_inventory_reserved_not_above_on_hand
               CHECK (reserved_quantity <= on_hand_quantity),

           CONSTRAINT chk_inventory_reorder_level_non_negative
               CHECK (reorder_level >= 0)
);

CREATE TABLE inventory_movements (
         id BIGINT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
         inventory_id BIGINT NOT NULL,
         movement_type VARCHAR(30) NOT NULL,
         quantity INTEGER NOT NULL,
         reference_type VARCHAR(30),
         reference_id BIGINT,
         notes TEXT,
         created_at TIMESTAMPTZ NOT NULL DEFAULT CURRENT_TIMESTAMP,

         CONSTRAINT fk_inventory_movements_inventory
             FOREIGN KEY (inventory_id)
                 REFERENCES inventory (id),

         CONSTRAINT chk_inventory_movement_quantity_positive
             CHECK (quantity > 0),

         CONSTRAINT chk_inventory_movement_type
             CHECK (
                 movement_type IN (
                                   'STOCK_RECEIVED',
                                   'STOCK_ADJUSTED',
                                   'ORDER_RESERVED',
                                   'ORDER_RELEASED',
                                   'ORDER_FULFILLED'
                     )
                 )
);

CREATE INDEX idx_inventory_movements_inventory_id
    ON inventory_movements (inventory_id);

CREATE INDEX idx_inventory_movements_created_at
    ON inventory_movements (created_at);