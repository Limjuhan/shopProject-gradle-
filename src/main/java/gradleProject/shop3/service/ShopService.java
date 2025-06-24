package gradleProject.shop3.service;

import gradleProject.shop3.repository.ItemRepository;
import gradleProject.shop3.domain.Item;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.util.List;

@Service 
public class ShopService {

	@Autowired  
	private ItemRepository itemRepository;

	public List<Item> itemList() {
		return itemRepository.findAll();
	}
	
	public Item getItem(Integer id) {
		return itemRepository.findById(id).get();
	}

	public void itemCreate(Item item, HttpServletRequest request) {
		// item.getPicture() : 업로드 된 파일이 존재. 파일의 내용 저장
		if(item.getPicture() !=null && !item.getPicture().isEmpty()) {
			// 업로드 폴더 지정
			String path = request.getServletContext().getRealPath("/")+"img/";
			uploadFileCreate(item.getPicture(),path);
			item.setPictureUrl(item.getPicture().getOriginalFilename());
		}
		int maxid = itemRepository.findmaxId(); // db에서 id의 최대 값 조회
		item.setId(maxid +1);
		itemRepository.save(item);
	}

	// 파일 업로드하기
	private void uploadFileCreate(MultipartFile picture, String path) {
		String orgFile = picture.getOriginalFilename(); // 원본 파일의 이름
		File f = new File(path);
		if(!f.exists()) {
			f.mkdirs(); // 폴더가 없으면 생성
		}
		try {
			// picture : 파일의 내용
			// transferTo : picture 의 내용을 new File(path+orgFile)의 위치로 저장
			picture.transferTo(new File(path+orgFile));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void itemUpdate(Item item, HttpServletRequest request) {
		if(item.getPicture() != null && !item.getPicture().isEmpty()) {
			String path = request.getServletContext().getRealPath("/")+"img/";
			uploadFileCreate(item.getPicture(),path);
			item.setPictureUrl(item.getPicture().getOriginalFilename());
		}
		itemRepository.save(item);
	}

	public void itemDelete(Integer id) {
		itemRepository.deleteById(id);
	}

//	public Sale checkEnd(User loginUser, Cart cart) {
//		int maxsaleid = saleDao.getMaxSaleId();
//		Sale sale = new Sale();
//		sale.setSaleid(maxsaleid + 1);
//		sale.setUser(loginUser);
//		sale.setUserid(loginUser.getUserid());
//
//		saleDao.insert(sale);
//		int seq = 0;// 주문상품 번호
//		for (ItemSet is : cart.getItemSetList()) {
//			SaleItem saleItem = new SaleItem(sale.getSaleid(), ++seq, is);
//			sale.getItemList().add(saleItem);
//			saleItemDao.insert(saleItem);
//		}
//
//		return sale;
//	}
//
//	public List<Sale> saleList(String userid) {
//
//		// userid 사용자가 주문정보 목록
//		List<Sale> list = saleDao.list(userid);
//
//		for (Sale s : list) {//Sale 순회
//			// Sale객체 List<SaleItem>(주문상품모음리스트)에 데이터 할당.
//
//			// 1. saleitem의 saleid가 Sale의 saleid를 참조하므로
//			//    saleid로 saleitem에서 데이터 가져옴
//			List<SaleItem> saleItemList = saleItemDao.list(s.getSaleid());
//			// 2. 주문상품을 모아둔saleItemList을 순회하며 Item정보를 조회하여 Item데이터 세팅
//			for (SaleItem si : saleItemList) {
//				Item item = itemDao.select(si.getItemid());
//				si.setItem(item);
//			}
//			// 3. item정보를 세팅한 리스트를 각 Sale객체에 데이터 세팅
//			s.setItemList(saleItemList);
//		}
//		return list;
//	}
//
//	public void exchangeCreate() {
//		Document doc = null;
//		List<List<String>> trlist = new ArrayList<List<String>>();
//		String url = "https://www.koreaexim.go.kr/wg/HPHKWG057M01";
//		String exdate = null;
//
//		try {
//			doc = Jsoup.connect(url).get();
//			Elements trs = doc.select("tr");
//			exdate = doc.select("p.table-unit").html();
//
//			for (Element tr : trs) {
//				List<String> tdlist = new ArrayList<String>();
//				Elements tds = tr.select("td");
//				for (Element td : tds) {
//					tdlist.add(td.html());
//				}
//				if (tdlist.size() > 0) {
//					if (tdlist.get(0).equals("USD") || tdlist.get(0).equals("CNH") ||
//						tdlist.get(0).equals("JPY(100)") || tdlist.get(0).equals("EUR")) {
//							trlist.add(tdlist);
//						}
//				}
//			}
//		} catch(IOException e) {
//			e.printStackTrace();
//		}
//
//		for (List<String> tds :  trlist) {
//			Exchange ex = new Exchange(
//					0, tds.get(0), tds.get(1),
//					Float.parseFloat(tds.get(2).replace(",", "")),
//					Float.parseFloat(tds.get(3).replace(",", "")),
//					Float.parseFloat(tds.get(4).replace(",", "")),
//					exdate.trim());
//			exDao.insert(ex);
//		}
//
//	}

}

























