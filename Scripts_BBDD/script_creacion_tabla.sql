-- Table: discotecas

-- DROP TABLE discotecas;

CREATE TABLE discotecas
(
  "idDiscoteca" bigint NOT NULL,
  nombre text,
  descripcion text,
  latitud double precision,
  longitud double precision,
  CONSTRAINT discotecas_pkey PRIMARY KEY ("idDiscoteca")
)
WITH (
  OIDS=FALSE
);
ALTER TABLE discotecas
  OWNER TO lmvpnrdswrmekj;