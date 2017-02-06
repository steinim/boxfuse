#! /usr/bin/env bash

vpc=$(envchain aws_bekk aws ec2 describe-vpcs --filters "Name=tag-value, Values=boxfuse_vpc" | jq '.Vpcs[0].VpcId' | sed s/\"//g)
db_status="unknown"

db_status=$(envchain aws_bekk aws rds describe-db-instances --filters "Name=db-instance-id, Values=hello" | jq '.DBInstances[].DBInstanceStatus' | sed s/\"//g)

while [  "${db_status}" != "available" ]; do
  sleep 1
  db_status=$(envchain aws_bekk aws rds describe-db-instances --filters "Name=db-instance-id, Values=hello" | jq '.DBInstances[].DBInstanceStatus' | sed s/\"//g)
  echo db status is ${db_status}
done
