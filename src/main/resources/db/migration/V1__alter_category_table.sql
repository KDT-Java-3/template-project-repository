ALTER table category
      add rg_dt datetime not null default current_timestamp,
      add ud_dt datetime not null default current_timestamp ON UPDATE CURRENT_TIMESTAMP;