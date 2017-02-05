#! /usr/bin/env bash

vpc=$(envchain aws_bekk aws ec2 describe-vpcs --filters "Name=tag-value, Values=boxfuse_vpc" | jq '.Vpcs[0].VpcId' | sed s/\"//g)
security_group=$(envchain aws_bekk aws ec2 describe-security-groups --filters "Name=vpc-id, Values=${vpc}" | jq '.SecurityGroups[0].GroupId' | sed s/\"//g)
subnets=$(envchain aws_bekk aws ec2 describe-subnets --filters "Name=vpc-id, Values=${vpc}, Name=tag-value, Values=boxfuse_vpc_subnet*" | jq '.Subnets[].SubnetId' | tr '\n' ',' | sed s/,$// | sed s/\"//g)
db_url=$(envchain aws_bekk aws rds describe-db-instances --filters "Name=db-instance-id, Values=hello" | jq '.DBInstances[].Endpoint.Address' | sed s/\"//g)

echo "boxfuse create -env=test -vpc=${vpc} -securitygroup=${security_group} -subnets=${subnets} -db.type=none -db.default.url=${db_url} -db.default.username=hello"
echo "boxfuse run -env=test"
