package com.kianama3.console.sections.clients.status;

import java.util.Comparator;

public class ClientsStatusComparator implements Comparator<Object> {
	protected int m_sortCol;
	protected boolean m_sortAsc;

	public ClientsStatusComparator(int sortCol, boolean sortAsc) {
		m_sortCol = sortCol;
		m_sortAsc = sortAsc;
	}

	@Override
	public int compare(Object o1, Object o2) {
		if (!(o1 instanceof ClientStatusRecordData)
				|| !(o2 instanceof ClientStatusRecordData))
			return 0;
		ClientStatusRecordData s1 = (ClientStatusRecordData) o1;
		ClientStatusRecordData s2 = (ClientStatusRecordData) o2;
		int result = 0;
		switch (m_sortCol) {
		case 0:
			result = s1.m_name.compareTo(s2.m_name);
			break;
		case 1:
			String[] c1 = s1.m_ip.split("\\.");
			String[] c2 = s2.m_ip.split("\\.");
			for (int i=0;i<4;i++){
				//result = Integer.compare(Integer.parseInt(c1[i]), Integer.parseInt(c2[i]));
				int p1 = Integer.parseInt(c1[i]);
				int p2 = Integer.parseInt(c2[i]);
				result = p1 < p2 ? -1 : (p1 > p2 ? 1 : 0);
				if(result != 0) break;
			}
			//result = s1.m_ip.compareTo(s2.m_ip);
			break;
		case 2: // client state not type
			int cState1 = ((Integer) s1.m_clientState.m_state).intValue();
			int cSatte2 = ((Integer) s2.m_clientState.m_state).intValue();
			result = cState1 < cSatte2 ? -1 : (cState1 > cSatte2 ? 1 : 0);
			break;
		case 3: // monitor state not type
			int mState1 = ((Integer) s1.m_monitorState.m_state).intValue();
			int mState2 = ((Integer) s2.m_monitorState.m_state).intValue();
			result = mState1 < mState2 ? -1 : (mState1 > mState2 ? 1 : 0);
			break;
		case 4: // update state not type
			int uState1 = ((Integer) s1.m_updateState.m_state).intValue();
			int uState2 = ((Integer) s2.m_updateState.m_state).intValue();
			result = uState1 < uState2 ? -1 : (uState1 > uState2 ? 1 : 0);
			break;
		}
		if (!m_sortAsc)
			result = -result;
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (obj instanceof ClientsStatusComparator) {
			ClientsStatusComparator compObj = (ClientsStatusComparator) obj;
			return (compObj.m_sortCol == m_sortCol)
					&& (compObj.m_sortAsc == m_sortAsc);
		}
		return false;
	}
}