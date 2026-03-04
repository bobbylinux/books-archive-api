CREATE TABLE public.authors
(
    id bigserial NOT NULL,
    first_name character varying(255) NOT NULL,
    last_name character varying(255) NOT NULL,
    created_at timestamp without time zone NOT NULL DEFAULT current_timestamp,
    modified_at timestamp without time zone,
    deleted_at timestamp without time zone,
    CONSTRAINT pk_authors PRIMARY KEY (id),
    CONSTRAINT unique_authors_first_name_last_name UNIQUE (first_name, last_name)
);

ALTER TABLE IF EXISTS public.authors
    OWNER to books_archive;
