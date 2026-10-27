import React, { useState } from 'react';
import { 
  Users, 
  Map as MapIcon, 
  PawPrint, 
  Leaf, 
  AlertTriangle, 
  CalendarCheck, 
  LogOut, 
  Home, 
  ShieldCheck,
  ChevronRight,
  Bell,
  Search,
  Plus
} from 'lucide-react';
import { motion, AnimatePresence } from 'motion/react';

type Role = 'ADMIN' | 'RANGER' | 'VISITOR';

export default function App() {
  const [isLoggedIn, setIsLoggedIn] = useState(false);
  const [user, setUser] = useState<{name: string, role: Role} | null>(null);
  const [activeTab, setActiveTab] = useState('dashboard');

  const handleLogin = (e: React.FormEvent) => {
    e.preventDefault();
    setUser({ name: 'Radha', role: 'ADMIN' });
    setIsLoggedIn(true);
  };

  if (!isLoggedIn) {
    return (
      <div className="min-h-screen bg-green-50 flex items-center justify-center p-4">
        <motion.div 
          initial={{ opacity: 0, y: 20 }}
          animate={{ opacity: 1, y: 0 }}
          className="bg-white p-8 rounded-3xl shadow-2xl w-full max-w-md border-t-8 border-green-700"
        >
          <div className="text-center mb-10">
            <h1 className="text-4xl font-black text-green-900 tracking-tight">FAUNEX</h1>
            <p className="text-green-600 font-medium mt-2">Wildlife Conservation Preview</p>
          </div>

          <form onSubmit={handleLogin} className="space-y-6">
            <div>
              <label className="block text-gray-700 text-sm font-bold mb-2 ml-1">Username</label>
              <input 
                type="text" 
                defaultValue="admin"
                className="w-full px-4 py-3 bg-gray-50 border border-gray-200 rounded-xl focus:ring-2 focus:ring-green-500 outline-none transition-all" 
              />
            </div>
            <div>
              <label className="block text-gray-700 text-sm font-bold mb-2 ml-1">Password</label>
              <input 
                type="password" 
                defaultValue="admin123"
                className="w-full px-4 py-3 bg-gray-50 border border-gray-200 rounded-xl focus:ring-2 focus:ring-green-500 outline-none transition-all" 
              />
            </div>
            <button 
              type="submit" 
              className="w-full bg-green-700 hover:bg-green-800 text-white font-bold py-4 rounded-xl shadow-lg transition-all transform hover:-translate-y-0.5 active:scale-95"
            >
              SIGN IN
            </button>
          </form>
          
          <div className="mt-8 pt-6 border-t border-gray-100">
            <p className="text-center text-xs text-gray-400 uppercase font-bold tracking-widest">Preview Mode Only</p>
            <p className="text-center text-[10px] text-gray-400 mt-1 italic">Java code files are available in the editor for download.</p>
          </div>
        </motion.div>
      </div>
    );
  }

  return (
    <div className="min-h-screen bg-gray-50 flex">
      {/* Sidebar */}
      <aside className="w-64 bg-green-900 text-white flex flex-col shadow-2xl">
        <div className="p-8 text-center border-b border-green-800">
          <h1 className="text-2xl font-black tracking-widest italic">FAUNEX</h1>
        </div>
        
        <nav className="flex-grow p-4 mt-4 space-y-2">
          <SidebarLink icon={<Home size={20} />} label="Dashboard" active={activeTab === 'dashboard'} onClick={() => setActiveTab('dashboard')} />
          <SidebarLink icon={<Leaf size={20} />} label="Species" active={activeTab === 'species'} onClick={() => setActiveTab('species')} />
          <SidebarLink icon={<PawPrint size={20} />} label="Animals" active={activeTab === 'animals'} onClick={() => setActiveTab('animals')} />
          <SidebarLink icon={<MapIcon size={20} />} label="Habitats" active={activeTab === 'habitats'} onClick={() => setActiveTab('habitats')} />
          <SidebarLink icon={<ShieldCheck size={20} />} label="Rangers" active={activeTab === 'rangers'} onClick={() => setActiveTab('rangers')} />
          <SidebarLink icon={<Users size={20} />} label="Visitors" active={activeTab === 'visitors'} onClick={() => setActiveTab('visitors')} />
          <div className="pt-4 mt-4 border-t border-green-800">
            <SidebarLink icon={<AlertTriangle size={20} />} label="Incidents" active={activeTab === 'incidents'} onClick={() => setActiveTab('incidents')} />
            <SidebarLink icon={<CalendarCheck size={20} />} label="Visits" active={activeTab === 'visits'} onClick={() => setActiveTab('visits')} />
          </div>
        </nav>
        
        <div className="p-4 border-t border-green-800">
          <button 
            onClick={() => setIsLoggedIn(false)}
            className="flex items-center space-x-3 p-3 w-full text-red-300 hover:text-white hover:bg-red-900/30 rounded-xl transition-all"
          >
            <LogOut size={20} />
            <span className="font-bold">Logout</span>
          </button>
        </div>
      </aside>

      {/* Main Content */}
      <main className="flex-grow overflow-y-auto">
        <header className="bg-white border-b border-gray-100 p-6 flex justify-between items-center sticky top-0 z-10">
          <div>
            <h2 className="text-xl font-bold text-gray-800">Faunex Command Center</h2>
            <p className="text-xs text-gray-400 font-medium">{new Date().toLocaleDateString('en-US', { weekday: 'long', year: 'numeric', month: 'long', day: 'numeric' })}</p>
          </div>
          <div className="flex items-center space-x-4">
            <div className="relative group">
              <Search className="absolute left-3 top-1/2 -translate-y-1/2 text-gray-400" size={18} />
              <input 
                type="text" 
                placeholder="Search..." 
                className="pl-10 pr-4 py-2 bg-gray-100 rounded-full text-sm outline-none w-48 focus:w-64 focus:bg-white focus:ring-2 focus:ring-green-100 transition-all" 
              />
            </div>
            <div className="w-10 h-10 bg-green-100 text-green-700 rounded-full flex items-center justify-center font-bold relative cursor-pointer">
              {user?.name[0]}
              <div className="absolute -top-1 -right-1 w-4 h-4 bg-red-500 rounded-full border-2 border-white"></div>
            </div>
          </div>
        </header>

        <div className="p-8">
          <AnimatePresence mode="wait">
            {activeTab === 'dashboard' ? (
              <motion.div 
                key="dashboard"
                initial={{ opacity: 0, x: -10 }}
                animate={{ opacity: 1, x: 0 }}
                exit={{ opacity: 0, x: 10 }}
              >
                <div className="grid grid-cols-1 md:grid-cols-2 lg:grid-cols-4 gap-6 mb-8">
                  <StatCard label="Species" value="128" icon={<Leaf className="text-blue-500" />} bg="bg-blue-50" />
                  <StatCard label="Total Animals" value="452" icon={<PawPrint className="text-orange-500" />} bg="bg-orange-50" />
                  <StatCard label="Habitats" value="15" icon={<MapIcon className="text-green-500" />} bg="bg-green-50" />
                  <StatCard label="Monthly Visitors" value="1,240" icon={<Users className="text-purple-500" />} bg="bg-purple-50" />
                </div>

                <div className="grid grid-cols-1 lg:grid-cols-3 gap-8">
                  <div className="lg:col-span-2 space-y-8">
                    <section className="bg-white rounded-3xl shadow-sm border border-gray-100 p-6">
                      <div className="flex justify-between items-center mb-6">
                        <h3 className="font-bold text-gray-800 text-lg">Recent Incidents</h3>
                        <button className="text-xs font-bold text-green-700 hover:underline">VIEW ALL</button>
                      </div>
                      <div className="space-y-4">
                        <IncidentItem type="Health Check" animal="Simba (Lion)" location="Zone A4" status="High" time="2h ago" />
                        <IncidentItem type="Fence Repair" animal="N/A" location="South Perimeter" status="Medium" time="5h ago" />
                        <IncidentItem type="Feeding Log" animal="Tofu (Panda)" location="Zone B1" status="Low" time="8h ago" />
                      </div>
                    </section>

                    <section className="bg-white rounded-3xl shadow-sm border border-gray-100 p-6">
                      <div className="flex justify-between items-center mb-6">
                        <h3 className="font-bold text-gray-800 text-lg">Conservation Progress</h3>
                      </div>
                      <div className="flex items-end justify-between h-48 px-10">
                        <div className="w-12 bg-green-700 rounded-t-xl" style={{height: '60%'}}></div>
                        <div className="w-12 bg-green-600 rounded-t-xl" style={{height: '80%'}}></div>
                        <div className="w-12 bg-green-500 rounded-t-xl" style={{height: '40%'}}></div>
                        <div className="w-12 bg-green-400 rounded-t-xl" style={{height: '90%'}}></div>
                        <div className="w-12 bg-green-300 rounded-t-xl" style={{height: '55%'}}></div>
                      </div>
                    </section>
                  </div>

                  <div className="space-y-8">
                    <section className="bg-green-800 p-8 rounded-3xl text-white relative overflow-hidden shadow-xl">
                      <Leaf size={120} className="absolute -right-8 -bottom-8 opacity-10 rotate-12" />
                      <h3 className="text-xl font-bold mb-4">Core Mission</h3>
                      <p className="text-green-100 text-sm leading-relaxed">
                        Faunex connects rangers and researchers to ensure the long-term survival of endangered species through real-time tracking and habitat optimization.
                      </p>
                      <button className="mt-6 px-4 py-2 bg-white/10 hover:bg-white/20 rounded-lg text-xs font-bold transition-all">READ MORE</button>
                    </section>

                    <section className="bg-white rounded-3xl shadow-sm border border-gray-100 p-6 text-center">
                      <div className="w-16 h-16 bg-blue-50 text-blue-500 rounded-full flex items-center justify-center mx-auto mb-4">
                        <CalendarCheck size={28} />
                      </div>
                      <h4 className="font-bold text-gray-800">Visit Schedule</h4>
                      <p className="text-xs text-gray-400 mt-1">Next tour starts in 45 mins</p>
                      <button className="mt-4 w-full py-2 border border-gray-100 rounded-xl text-sm font-bold text-gray-700 hover:bg-gray-50 transition-all">MANAGE SLOTS</button>
                    </section>
                  </div>
                </div>
              </motion.div>
            ) : (
              <motion.div 
                key="list"
                initial={{ opacity: 0, y: 10 }}
                animate={{ opacity: 1, y: 0 }}
                className="bg-white rounded-3xl shadow-sm border border-gray-100 overflow-hidden"
              >
                <div className="p-8 border-b border-gray-100 flex justify-between items-center">
                    <h3 className="text-2xl font-bold text-gray-800 capitalize leading-none">{activeTab} Management</h3>
                    <button className="flex items-center space-x-2 px-4 py-2 bg-green-700 text-white rounded-xl text-sm font-bold shadow-lg shadow-green-900/20 hover:scale-105 transition-all">
                        <Plus size={18} />
                        <span>ADD NEW</span>
                    </button>
                </div>
                <div className="p-8 text-center py-20 text-gray-400 italic">
                    This is a preview interface. The full Java implementation with MySQL database logic is available in the project files. 
                    <br/>
                    <span className="text-xs font-bold text-green-700 uppercase block mt-2">See README_JAVA.md for setup</span>
                </div>
              </motion.div>
            )}
          </AnimatePresence>
        </div>
      </main>
    </div>
  );
}

function SidebarLink({ icon, label, active, onClick }: { icon: React.ReactNode, label: string, active?: boolean, onClick: () => void }) {
  return (
    <button 
      onClick={onClick}
      className={`sidebar-link w-full flex items-center space-x-3 px-4 py-3 rounded-xl transition-all cursor-pointer ${active ? 'bg-white/15 text-white shadow-inner font-bold' : 'text-green-300 hover:text-white hover:bg-white/5'}`}
    >
      {icon}
      <span>{label}</span>
    </button>
  );
}

function StatCard({ label, value, icon, bg }: { label: string, value: string, icon: React.ReactNode, bg: string }) {
  return (
    <div className="bg-white p-6 rounded-3xl shadow-sm border border-gray-100 hover:shadow-md transition-shadow">
      <div className="flex items-center justify-between">
        <div>
          <p className="text-gray-400 text-xs font-bold uppercase tracking-wider">{label}</p>
          <h3 className="text-3xl font-black text-gray-800 mt-1">{value}</h3>
        </div>
        <div className={`${bg} p-4 rounded-2xl`}>
          {icon}
        </div>
      </div>
    </div>
  );
}

function IncidentItem({ type, animal, location, status, time }: { type: string, animal: string, location: string, status: 'High' | 'Medium' | 'Low', time: string }) {
  const statusColor = {
    High: 'bg-red-100 text-red-600',
    Medium: 'bg-yellow-100 text-yellow-600',
    Low: 'bg-green-100 text-green-600'
  };

  return (
    <div className="flex items-center justify-between p-4 rounded-2xl hover:bg-gray-50 transition-colors border border-transparent hover:border-gray-100 group">
      <div className="flex items-center space-x-4">
        <div className={`w-2 h-10 rounded-full ${status === 'High' ? 'bg-red-500' : status === 'Medium' ? 'bg-yellow-500' : 'bg-green-500'}`}></div>
        <div>
          <p className="font-bold text-gray-800 group-hover:text-green-700 transition-colors">{type}</p>
          <p className="text-xs text-gray-400 font-medium">{animal} • {location}</p>
        </div>
      </div>
      <div className="text-right">
        <span className={`px-3 py-1 rounded-full text-[10px] font-black uppercase tracking-widest ${statusColor[status]}`}>{status}</span>
        <p className="text-[10px] text-gray-400 mt-1 font-bold">{time}</p>
      </div>
    </div>
  )
}
