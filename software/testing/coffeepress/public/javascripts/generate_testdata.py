#!/usr/bin/python
import sys
import math
import datetime

from pymongo import MongoClient
import json

def main():
	# Variables
	maxint = sys.maxint
	minint = -sys.maxint - 1
	
	# Connection to Mongo
	client = MongoClient()
	# Get DB
	db = client.soundgates
	# Get Collection
	executions = db.executions

	# Create Components

	# print json.dumps(vars(james),sort_keys=True, indent=4)
	
def generateSine(samples)
	data = []
	for x in range(0, samples):
		data.append( Math.sin(x) )
	return data

def generateSine(samples)
	data = []
	for x in range(0, samples):
		data.append( Math.cos(x) )
	return data



class Execution(object):
	def __init__(self, components = None):
		self.Components = components or []
		self.Timestamp = datetime.now()

	def addComponent(self, component):
		self.Components.append(component)

class Component(object):
	def __init__(self, uid, typ, control_ports = None, input_samples = None, output_samples = None, execution_times = None):
		self.Uid = uid
		self.Typ = typ
		self.ControlPorts = control_ports or []
		self.InputSamples = input_samples or []
		self.OutputSamples = output_samples or []
		self.ExecutionTimes = execution_times or []

if __name__ == "__main__":
    main()