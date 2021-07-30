#!/bin/bash
psql fitwf -U fitwf_admin<<-EOSQL
CREATE SCHEMA IF NOT EXISTS fitwf;
EOSQL