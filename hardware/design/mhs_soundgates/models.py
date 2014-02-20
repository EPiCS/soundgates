'''
Created on 22.01.2014

@author: CaiusC
'''

import re
import csv
import subprocess
import shutil
import os
import sys

class TGF(object):
    '''
    This class contains every Hardware component and is responsible for creating the XPS project.
    '''    
    def __init__(self, tgf_file, component_path, base_design):
        '''
        Constructor
        '''
        self.Library = Library(component_path)
        self.BaseDesign = base_design
        self.Components = []
        print 'Info: Parsing tfl: ', tgf_file
        with open(tgf_file, "r") as f:
            for line in f:
                if ( self.isHardwareComponent(line) ):
                    comp = HardwareComponent(line, self.Library)
                    self.Components.append(comp)
        print 'Info: Found ', len(self.Components) , ' Hardware components'
                    
    def printComponents(self):
        '''
        Prints every hardware component which has been found inside of the tgf file. 
        '''
        print 'Info: Component count: ', self.getComponentCount()
        print 'Info: Printing Hardware Components: '
        for c in self.Components:
            c.printStatus()
    
    def getUniqueComponentCount(self):
        '''
        Returns the number of distinct hardware components.
        '''
        components = []
        for c in self.Components:
            components.append(c.getName())
        unique = set(list)
        return len(unique)
    
    def getUniqueComponents(self):
        '''
        Returns a list of unique hardware components.
        '''
        components = []
        for c in self.Components:
            components.append(c.getName())
        unique = set(list)
        return unique
            
    def getComponentCount(self):
        '''
        Returns the number of Hardware Components from the specified tgf file
        '''
        return len(self.Components)
                    
    def isHardwareComponent(self, line):
        '''
        Returns if the specified line from the tgf file accords to a hardware component
        '''
        words = line.split(' ')
        for w in words:
            if not (w.find("hw") == -1):
                return True
        return False
    
    def createSetupFile(self):
        '''
        Creates the setup_zynq file which is needed by the reconos mhs creator.
        '''
        path = "project/setup_zynq"
        # Prepare content
        base_design = 'base_design=' + self.BaseDesign
        num_static_hwts = 'num_static_hwts=' + str(self.getComponentCount())
        implementations = 'static_hwts="'
        used_components = set()
        first = True
        for c in self.Components:
            if(c.getType() not in used_components):
                used_components.add(c.getType())
                if c.getImplementation() is None:
                    print 'Error: Could not find ' + c.Id + ' inside of the components.csv'
                    sys.exit(1)
                if first:
                    implementations = implementations + c.getImplementation() + '#' + str(c.getComponentCount())
                    first = False
                else:
                    implementations = implementations + ' ' + c.getImplementation() + '#' + str(c.getComponentCount())
        implementations = implementations + '"'
        # Check preconditions
        if not (os.path.exists("project")):
            os.makedirs("project")
        if os.path.isfile(path):
            os.remove(path)
        # Write file
        with open(path, 'a') as f:
            f.write(base_design + '\n')
            f.write(num_static_hwts+ '\n')
            f.write(implementations + '\n')

    
    def linkComponents(self):
        '''
        Every pcore has to be linked into the project folder
        '''
        # Delegating the linking to the hardware components
        for c in self.Components:
            c.export(self.Library)
            

    def reconosSetup(self):
        # Executes the reconos_setup script
        subprocess.call(['reconos/reconos_setup.sh', 'project/setup_zynq'])
        
    def directScript(self):
        '''
        Can be used to skip the reconos_setup.sh script. But actually it is not fully implemented :/
        '''
        #mhsaddhwts.py <architecture> <system.mhs> <num_static_hwts> <num_reconf_regions> <hwt0_directory>[#<count>] <hwt1_directory>[#<count>]
        arch = "zynq"
        mhs_path = "reconos/basedesign_audio_reconos.mhs"
        num_static_hwts = str(self.getComponentCount())
        num_reconf = 0
        implementations = ''
        used_components = set()
        for c in self.Components:
            if(c.getType() not in used_components):
                used_components.add(c.getType())
                if c.getImplementation() is None:
                    print 'Error: Could not find ' + c.Id + ' inside of the components.csv'
                    sys.exit(1)
                implementations = implementations + c.getImplementation() + '#' + str(c.getComponentCount())+ ' ' 
        implementations = implementations + '"'
        subprocess.call(['reconos/mhsaddhwts.py', arch, mhs_path, num_static_hwts, num_reconf, implementations])

    def getLibrary(self):
        return self.Library

class HardwareComponent(object):
    '''
    Represents a Component which is used by the current Patch
    '''
    # Class variables
    c_dict = dict()
    linked_set = set()
    
    def __init__(self, line, library):
        '''
        Constructor
        '''
        wordList = re.sub("[^\w]", " ",  line).split()
        self.Id   = wordList[0]
        self.Type = wordList[1]
        self.Slot = wordList[3]
        self.IsLinked = False # Every component has to be linked one time into the project folder
        self.Implementation = library.getImplementation(self.Type)

        if HardwareComponent.c_dict.has_key(self.Type):
            count = HardwareComponent.c_dict[self.Type]
            count = count + 1
            HardwareComponent.c_dict.update({self.Type : count})
        else:
            HardwareComponent.c_dict.update({self.Type : 1}) 
    
    def getComponentCount(self):
        if HardwareComponent.c_dict.has_key(self.Type):
            return HardwareComponent.c_dict[self.Type]
        else:
            print 'Error: Did not find ', self.Type, ' in dictionary. This should not happen bro :('
            return 0
    
    def printStatus(self):
        print 'Id: ',   self.Id
        print 'Type: ', self.Type
        print 'Implementation', self.Implementation
        print 'Slot: ', self.Slot
        print ''
        
    def export(self, library):
        '''
        Every distinct hardware pcore has to be linked exactly one time into the project folder.
        That's why we need the Class variable "linked_set"
        '''
        if self.Type in HardwareComponent.linked_set:
            return
        else:
            HardwareComponent.linked_set.add(self.Type) 
            src = library.getPath(self.Type)
            dst = os.path.join('project/', self.Implementation)
            if (os.path.isdir(dst)):
                os.unlink(dst)
            os.symlink(src, dst)
            self.IsLinked = True
    
    def getType(self):
        return self.Type
    
    def getImplementation(self):
        return self.Implementation
    
    def getSlot(self):
        return self.Slot


class Library(object):
    '''
    This class contains every available Hardware component.
    '''
    
    # Initializes the Library by reading the components csv File    
    def __init__(self, component_path):
        self.ComponentPath = component_path
        self.Components = []
        with open('components.csv', 'rb') as f:
            reader = csv.reader(f)
            for row in reader:
                self.Components.append(LibraryComponent(row[0], row[1], self.ComponentPath))


    def getComponentList(self):
        return self.Components

    # Returns the path of the given component
    def getPath(self, abbreviation):
        for c in self.Components:
            if(c.getAbbreviation() == abbreviation):
                return c.getPath()
        print "Warning: Did not find component: ", c.getAbbreviation
        return None
    
    def getImplementation(self, abb):
        for c in self.Components:
            if abb == c.getAbbreviation():
                return c.getImplementation()


class LibraryComponent(object):
    '''
    This class represents a single Hardware Component
    '''
    def __init__(self, abbreviation, implementation, componentPath):
        self.Abbreviation = abbreviation
        self.Implementation = implementation
        if componentPath is None:
            componentPath = os.getenv("SOUNDGATES")
            componentPath = os.path.join(componentPath, 'hardware/hwt/pcores/')
        self.Path = os.path.join(componentPath, self.getImplementation())
        self.Count = 0

    def getAbbreviation(self):
        return self.Abbreviation

    def getImplementation(self):
        return self.Implementation
    
    def getPath(self):
        return self.Path
    
    def increaseCount(self):
        self.Count = self.Count + 1
        
    def getCount(self):
        return self.Count
        
