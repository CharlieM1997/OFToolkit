"""Custom topology example
One switch plus 3 host :


Adding the 'topos' dict with a key/value pair to generate our newly defined
topology enables one to pass in '--topo=mytopo' from the command line.

This topo used to test ARP poisoning

Only use the arp.txt file located in this directory while performing this experiment.
Otherwise create your arp.txt file through the toolkit for your own topology.
"""

from mininet.topo import Topo

class MyTopo( Topo ):
    "Simple topology example."

    def __init__( self ):
        "Create custom topo."

        # Initialize topology
        Topo.__init__( self )

        # Add hosts and switches
		# The malicious host has the same MAC address as the right host, which makes the ingredients for ARP spoofing
        maliciousHost = self.addHost( 'h1',ip='10.0.0.2' , mac = '00:00:00:00:00:04')
        leftHost = self.addHost( 'h2',ip='10.0.0.3' , mac = '00:00:00:00:00:03')
        rightHost = self.addHost( 'h3',ip='10.0.0.4' , mac = '00:00:00:00:00:04')
        switch = self.addSwitch( 's1')



        # Add links
        self.addLink( leftHost,  switch)
        self.addLink( rightHost,  switch)
        self.addLink( maliciousHost,  switch)


topos = { 'mytopo': ( lambda: MyTopo() ) }
