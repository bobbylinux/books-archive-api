CREATE TABLE public.authors
(
    id bigserial NOT NULL,
    first_name character varying(255) NOT NULL,
    last_name character varying(255) NOT NULL,
    created_at timestamp without time zone NOT NULL DEFAULT current_timestamp,
    updated_at timestamp without time zone,
    deleted_at timestamp without time zone,
    CONSTRAINT pk_authors PRIMARY KEY (id)
);

CREATE UNIQUE INDEX unique_authors_first_name_last_name
ON authors (first_name, last_name)
WHERE deleted_at IS NULL;

ALTER TABLE IF EXISTS public.authors
    OWNER to books_archive;

CREATE TABLE public.publishers
(
    id bigserial NOT NULL, 
    name character varying(255),
    created_at timestamp without time zone NOT NULL DEFAULT current_timestamp,
    updated_at timestamp without time zone,
    deleted_at timestamp without time zone,
    CONSTRAINT pk_publishers PRIMARY KEY (id)
);

CREATE UNIQUE INDEX unique_publishers_name
ON publishers (name)
WHERE deleted_at IS NULL;

ALTER TABLE IF EXISTS public.publishers
    OWNER to books_archive;

CREATE TABLE public.photos
(
    id bigserial NOT NULL, 
    path character varying(500) NOT NULL,
    file_name character varying(255) NOT NULL,
    content_type character varying(50) NOT NULL,
    created_at timestamp without time zone NOT NULL DEFAULT current_timestamp,
    updated_at timestamp without time zone,
    deleted_at timestamp without time zone,
    CONSTRAINT pk_photos PRIMARY KEY (id)
);

CREATE UNIQUE INDEX unique_photo_path
ON photos (path)
WHERE deleted_at IS NULL;

ALTER TABLE IF EXISTS public.photos
    OWNER to books_archive;