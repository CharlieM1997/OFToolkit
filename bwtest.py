from mininet.net import Mininet
from mininet.topo import Topo
from mininet.node import Controller, RemoteController, OVSController
from mininet.node import CPULimitedHost, Host, Node
from mininet.node import OVSKernelSwitch
from mininet.cli import CLI
from mininet.log import setLogLevel, info
from mininet.link import TCLink, Intf
from subprocess import call

class newTopo( Topo ):
	def __init__( self ):
		# Initialize topology
		Topo.__init__( self )
		# Add hosts
		h1 = self.addHost( 'h1', mac='00:00:00:00:00:02' )
		h4 = self.addHost( 'h4', mac='00:00:00:00:00:05' )
		h3 = self.addHost( 'h3', mac='00:00:00:00:00:04' )
		h2 = self.addHost( 'h2', mac='00:00:00:00:00:03' )

		# Add switches
		s2 = self.addSwitch( 's2' )
		s3 = self.addSwitch( 's3' )
		s4 = self.addSwitch( 's4' )
		s1 = self.addSwitch( 's1' )

		# Add links
		self.addLink( h2, s1 )
		self.addLink( s2, s4, cls=TCLink,bw=10 )
		self.addLink( s1, s2, cls=TCLink,bw=10 )
		self.addLink( s3, s4 )
		self.addLink( s4, h4 )
		self.addLink( s4, h3 )
		self.addLink( s1, s3 )
		self.addLink( h1, s1 )


topos = {
	 'new': ( lambda: newTopo() )
}

def myNetwork():

	net = Mininet( topo=new )

	info ( '*** Adding controller\n' )
	c0=net.addController(name='c0',
		controller=RemoteController,
		ip='127.0.0.1',
		protocol='tcp',
		port=6633)
	net.start()
	CLI( net )
	net.stop()