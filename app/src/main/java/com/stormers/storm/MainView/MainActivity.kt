package com.stormers.storm.MainView

import android.os.Bundle
import android.view.Menu
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.widget.Toolbar
import androidx.core.view.GravityCompat
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.stormers.storm.R
import com.stormers.storm.base.BaseActivity
import com.stormers.storm.util.MarginDecoration
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.item_participated_projects_list.*

class MainActivity : BaseActivity() {

    lateinit var participatedProjectsAdapter: ParticipatedProjectsAdapter
    val datas = mutableListOf<ParticipatedProjectsData>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val mainview_toolbar = findViewById(R.id.mainview_toolbar) as Toolbar

        setSupportActionBar(mainview_toolbar)
        supportActionBar?.setHomeAsUpIndicator(R.drawable.mainview_ic_bamburgerbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        val ab = supportActionBar!!
        ab.setDisplayShowTitleEnabled(false)

        val drawerToggle: ActionBarDrawerToggle = object : ActionBarDrawerToggle(
            this,
            drawerlayout_main,
            mainview_toolbar,
            R.string.drawer_open,
            R.string.drawer_close
        ){
            override fun onDrawerClosed(view: View){
                super.onDrawerClosed(view)
            }
            override fun onDrawerOpened(drawerView: View){
                super.onDrawerOpened(drawerView)
            }
        }

        // Configure the drawer layout to add listener and show icon on toolbar
        drawerToggle.isDrawerIndicatorEnabled = true
        drawerlayout_main.addDrawerListener(drawerToggle)
        drawerToggle.syncState()

        navigationview_main.setNavigationItemSelectedListener{menuItem ->
            when(menuItem.itemId){
                R.id.item1 -> Toast.makeText(this,"item1 selected", Toast.LENGTH_SHORT).show()
                R.id.item2 -> Toast.makeText(this,"item2 selected", Toast.LENGTH_SHORT).show()
                R.id.item3 -> Toast.makeText(this,"item3 selected", Toast.LENGTH_SHORT).show()
            }
            drawerlayout_main.closeDrawer(GravityCompat.START)
            true
        }


        // ParticipatedProjectAdapter
        participatedProjectsAdapter =
            ParticipatedProjectsAdapter(this)
        recycler_participated_projects_list.adapter = participatedProjectsAdapter
        recycler_participated_projects_list.layoutManager = LinearLayoutManager(this, RecyclerView.HORIZONTAL, false)
        recycler_participated_projects_list.addItemDecoration(MarginDecoration(baseContext,16,RecyclerView.HORIZONTAL))
        recycler_participated_projects_list.addItemDecoration(DividerItemDecoration(baseContext,LinearLayoutManager.HORIZONTAL))
        loadProjectsDatas()
    }


    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.toolbar_main, menu)
        return super.onCreateOptionsMenu(menu)
    }

    private fun loadProjectsDatas() {

        val datas = mutableListOf<ParticipatedProjectsData>()

        datas.apply {
            add(
                ParticipatedProjectsData(
                    card1 = "data:image/jpeg;base64,/9j/4AAQSkZJRgABAQAAAQABAAD/2wCEAAkGBxMSEhUSEhIWFRUXFhYWFxcXFhcXFRcZFhcXGBcYFxUYHSggGBsmHhcVIjEhJSkrLi4uFx8zODMtNygtLysBCgoKDg0OGhAQGysmHyYvLS8vLS0uLSstLy0rKystLy0tLTAtKzctKysrLS0tLS0rLS0tLS0tLS8tLS0tLS0tLf/AABEIAOEA4QMBIgACEQEDEQH/xAAcAAABBAMBAAAAAAAAAAAAAAAAAQUGBwIDBAj/xABSEAACAQIEAwUDBQoJCwMFAAABAhEAAwQSITEFBkETIlFhcTKBkQcUobHBIzM0QmJykrLR8BUkUlNzdKKz4RY1Q4KDk6O0wtLxlMPiFyVFVWP/xAAZAQADAQEBAAAAAAAAAAAAAAAAAQMCBAX/xAA0EQACAgEDAgMHAwIHAQAAAAAAAQIRAxIhMQRBE1FxMjNhgaGxwSKR8FJyIzRCRNHh8QX/2gAMAwEAAhEDEQA/AKPpKWkrQBRRRSAKKKKQBRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUwCilopAApaSigQUUUCgAopctFACUlZUlaGJRRRSYBRRRQAUUUUAFFFFIAooopgFFFFIAooooAKKKKYBRRRSAKWiigAoopaBCUUUtACVlRSGgBZopKKYAaSlNJTASiiigYUUUUgCiiigAooooAKKWikAlFLSU6AKKKKKAKKKKACloopCCiiloAKKKKAClApKWgBDRSgVlFIDGKKyooAwNJS0VQBKKKKACiiikMSilooAKKWKIpCEopaIpoBKKWigBKKKKBhRFZCkrIgooooAKKKWgAoilApYpBRjWQWsgtZZaTkOjACkNZGsTSQUY0tFLWgMKKWiqGRKKWKKQxKKWikAlKBQBWcUAYgUsUsUGgbMDRQaStGQooooGFFFFABRRS1lgJRS0oFAgApQKUUtZbGgAp74XyrjMRaa9Zw1y5bWZZVkabwN2jrE02YOyWYKBJJAHqdquTnTiDcLv4O1Y0GGS0o84Ba7Pm+bU+dc+XLpovDHqIbyPylbvpdxOKzfN7OVcqkK1y45hUDEHKBuTG0V0c48nWUwtviGBZnwznK6sQz2XkiCw9pZEA+m8irQ+Ua1at8Le7hwFW/dF8xoCblknN78oPrUX+Ru6uJsYzht3VLlvOo8DojEeYJtn3VJSlraKUtNlLutampy4xg2s3XtMIZGKn1Bim1q6sbtWc81TEoooqpMWKIpaK0AlFLRWQCiis1FA0hAtZRS0EUG+DCsTWTGsa0TZiRRWVJFACUURSxTASiiiKQAKWilrLAKWKUCigAoFFZqKzI1Ee+UbYOLw4PW/aB/TWrE+WvCXHx6LbRnZkBCopZjoo2GvQ1Cvk8u4VMbabGGLSy0ywAde8hOXU6jarb4x8smCtE9ij33j2oFtdNhJlo91cc42/n+Gvyd0ISSTStft9TfheBYnGcEs4N0Nq8O790BAVVLhSYB/EI0+qtnJ3INnhL/OruLzOFZTmy2rUNEzMk7eNQO78qOPxhuC0y2AqFlCAST4FmBPjqI2qtuI8bv3zmu3Xc+LMWOvqdK1HHJu/5sDcVab/ZXz8XRYnyxY3h90A4Q22vdoz3XQaNm/L/ABulVUaGaaxroxw0qjmz5FNqlwq8xaKSlqlEDKilooASlorJVoGlYirWyK2MkKviSx92gH0hvhWMUFUqRjFIxpWMVrNa4JN2IaSloigQlFLRTFYlFLWSWydhP79aGCMAK3YVZaPEMP7JrdbRREgnxg/b0+mpdyby8l5zceCi91YMZiZ33Ogn+zWNRtIgorMLUm41wFbTCFm2wlHEajwgbEdd9vOKZ7+DyzBn3fWenwoszRxGsK3/ADdt4090fGkWw3h9VPgDBRWRrJkI3rAmpyNLYM1IWrIIayFsetJI3qHHlrGLbulnMKUYExOukVyWsAWOUDfRfH4Vlh7LMe6PU7ADXUnYVvu49bIyKuZj7b7Eg7qp3A8+vgK1Rizlx/CLlpgGXRtVMjUe4x4Vg3DHiRB8hv8A406JxlLpK3e4rRG5CEDQj7fGaR1e0YbYkEHdCNwQ2xFa3XBkY+xb+SfgaSnr55+T9Jop2/IexhyrhDdxmGtgKS15IDzlYhpytGsGI99T27yTYtXLiFDZMxlxDKVGk5bV32WSZ1LBtBOs1nhuTrLXQbN22txToUu9myz1Cqyny06GpPZ4OMMLa4i3dxFy4hjs7jK2jRLXEYHYqJM7VxZM11Wx0Y8S3shF/wCTQmTbLECNUIdfXSdD016U0YrkW+kgEHyIIPvnb4VeVzkO20OpdTHR7d06gfztqf7QrX/ktiU9nENHg9tz8OyvMo6fie6tXlXxBKHn9yhcby9fGQKmbKsGDGpdm/GjTvfAU2XcBdXe2/uUnx8PQ1f9/gmKEl1sXPziEnfo1q3HT8Y+tcF7hlyO9gGbrNvVf+G90n4eFNZpx5QOGrh/YoTs2Obut3fa0Pd/O8PfWurzv8PsOps3LF1c/cKn7nM6b3VtxqBqB1FVrz/wizg7y4ayoDKua4Td7Z5aCgZgoVe7rlUfjak6VXHl19iU8bjyRWlorqwGDNxgD3V6sdFAG8n99jVW0lbMJWaOyaJymDoDBgnwBrCp1avG3hINjNaa5C3GLQCEBKAZgJgg6g+1TP2YMkBBpPsKCdR3RpqdzHlUo5r7G5Yq7kdFd2CwmdGJeIjKsHKxPtajQQI+IpzW4BIVRroYAA18enjXXb4W5XPdItJ1Z+6vuG5+jr76arMaThwGBzOqIJYkAHbX6vrOlSO7xEWXt20M27QIYqcoMzmYkHcmDHkPCme/zDasIyYMMXYFWvMIgGNEWJG3lvUflm1Yz9XwpGk0iVrxS05ZHOa0WZgFPeQtIkDrE/QK1cR4Q6DtEIu2iJDrrA1PeA1WI1MRUXuAExUn5Nw2Ke72eGvBBkZ37QnslRFJZmgEiPFddRWHJR5Gk5cHBZxBRgw3Ug7AiR4j9tLdYPOyyZ7vSfDw9ANqk/GRctKG4hgcoLva7a2QO+jOpIE5okORJhspImKZnw2Gu/eMQoJk5LvcbroD1O3jRalwJxpjM2CP4rAjwP7NqS9gLigHs431AOvQjw+HjTli+F3rXtKY8R3h5HMNvornGNcAAkwJjqATE6jXw69KzUuwKjkt4Bj7UKPPf3KNTXXYwS9FznxbRNuijU+80lq4h3+j95re1mdLbhh5sQfE6DUUW09wo7reBkAOdBsogD4DSmHmmwFuKRsV+o/4074fAXB+Mw8YAUfpPJPumtHNvD2SzZuEqQSQMpnx3MzPd6qunjW1NN0LQ1uRrs66MLjrtrRT3d8rDMh/1T9lb+C4Pt37PMFJUkEzEiNCRtpOtb8bwS9b1ZZUfjAyv6W3xiqao8GNzm/hX/8AhY/Rb/upa0ZV8qKAtHpu7yjYykW7jAf1q7l6ja6twdT1qIYfiJtqQrFgrBQSQYBz6SIA9keHoKkl/mGwhZb7IzgN3ULKC2wGYaAwSdfjIqOYxsO4BsBgzPmdSysoiYggSfaP015eTk9GDdNMdcLzDd0g/T9lPOH5nudSD9NROxhW91dXzVvCpaqMNJkxt81+IH2/ZS4jj9twZRZjQlQSPrqDXgwriv3WHU/E1vxZ+ZnQh5Yi7Zu2cXfZwZW0TmcwQM2wLHQx1NQvF8g4WdLiKOk3GUnb+dIj/Gpnyhh7N5mGJRGUCFDqrjMSDmhgYIA386deO8NwtlA9q12moBS1cvWWA8RkuZdNNMvwqmOTSbs1NRbSKk45yIqW1axctDLmLs+ISXEDKV1jx0ArRw7B2QixiVtOtwkwocqFGYMEJGaSCAvWfI1JearrJN6yrdnFi4bV0hncNchFLnUibT6RtG9RXF25l7pSwpJYwsMxJkwi6wJgDTSK6IXNb8EpJQZx3L4jKJPr+/7zW44E5c95ltJGhbQnTZV3Y7eFcNzjy29MPbg/zlyC3qq+ytNGIuvcbPcYsx6kkn0E9KvGCXBFyvkfbnHrVnTDW8zfztz61t9NdZ9PCmPGYu5ebNddnPmdB6DYe6tYFbbNqek1oViWrdZuadMFwe5c2UgeJ2+J0rPjPCfm4QFpYzMDbQRr7/KsqcdWnuNwlVjIw1/fwqafJ/ibduzxAu6oxwjpbDMAWZiJVQdzA2FRTBoDeRWmCygjTYwOulSH+AFYFrbx+cpAEEj2l06HpWeop/pZTFa3RYHOuIGLsYtQwbLw/BYgQQQrW7jF9tjldx76pFxUhuYbE4dbnZswS4hW5kYFWU7q0HVfUUw3FnbX6/hU8MKXI8sr7GzB8VvWfvd1lHhMr+idKcP8olc/drCtt3kOR+k7aHamUikK10ESQqMNdPcvZTrAuDLv+VsenwrXf4fdQAwYgEEd5f0htTBkrpwj3klrbMB1yn6xQA72OLXV3JI8+8Pp1Hxrox/FhiLJssoGoZSvRhI1G+xI99NacbJ++20ua7xkf9Jf2VsFzDvs7Wzp7YkT+euvxFJ44tp0GqS4NnL9nJetlpHfygjUd8ZdT09qp+i3EMo0HxGh+kEfEEeVQWzhnUZkhwCDKMGEg6TGo+upXw/iiXSIcow3VxMe+QT6/RUc8Zcrgrh0vnk7e0u+J/3Nj9lFOuceKfpf/Giua35F9K8yLvdLsWZizMZJMkkncknc064DC6EkbKT8BNN+EWpLgELqUQSTA6bSJ38pom6QSZlwkXwokiPNfsEH6af+2urCsEJMwozDbfcttTtw3hA7OGRvdqPHWN9q6cThlLKxzdwGBGmojXSToTua5JO2Tsg/EuKup1tEehB+wU1PjWKhsgE9CCD5damGO4IpLNpr6z1Jkk1H+M4MW7aj8kH46/bTi1waTY34fjnZn2I9G+yK6L/NIjXNt46f4VFb+IHrv9HrXDiMQFXO0R0H8ph/0jr47eNdcMCkZlko6+Z+Pk3O0UsGNtEQE6qqg96NgxLMR1AbzqGXyXbMxJJ6nU/TXbbR790Ad53Onv6mpC/DcLhyiXbdzEXmhcouC3alvZJYAsRsdOlddxglFEG3J2RAW/Cu3D8Juu2VUObWFg5j5KntMfQGrDw3BbS6OBoQRbsKLYkiO9ectdfQ9CPrqS4J+zQfNra2hu2QAEjxZ/aY7bmueXWxVV3KrAyv+GfJ3iTrdtlPK4QkddiZP76Vq4dwuyMXathiykSxIHg2w26TVmMhfdmOknadqq3lhy2Jtt4Kx/sn9tEZvJGTvhD0aZRRZWE5ftEhhbuONouNlQAbQqgT08a4OemtYZLTrhbR7R3Ulx2nsxqA3s7/AFVZWHwAVAbdwnQgzl0IyiNRJ69aiPyy4BmsWnZMypddmKyIzIupk+UViEXHeXBSKWSWm6+L4Kf4KqXeJ4cOoFt8TYVlHdGVnQECIj3VafEeDpZuv83zFCdwcw7pOx36nxqueS8OLvFcKBoBfw7f7so30lfpr0FxjhNnVoUHXWADPSDoQPTxpddHXChYmoTa5Ka4/giEutp3kYj4GoNy+ofFIrAHNmEEAr7BiQfSrc5lwaLbbKzMCLkSSw2OgJ93U1VPCbPY4zDloAZVYEmBDowknWKfTNvDL4L8BmrxI/zuPeL4NZYki3CjYq0aeOUyJrhxvKqJHfIJE5W0j1YAgfCpYLkNmWCQdGWGHu8fhXLehtep30A1noKzHqN1yvqUn05BsRwO6moGYfkwfqM/QKOGvAZes7ddAw2qaWuH5rqg6amfHY/bUd4hbyYu9bPQlln8oLProPorojmWS4kJYnCmRpzJPrSdnU7wnLi33FsKgLGMxbKB72kLTRxDlns2ZQ8QSNYI0/LUQRXWskeGc7i+wxYK2Qwhip8QYI85p5tccKkK6h4EZge8fjTfisHcta9NpEEa+lcQJmd6pCnuuDEk+GSj/KA/ym/RWioz84PgKWt6YGKkWO/CsQCB2Zjy1p24V2tphmRhHjrTj/BPDJ/DMkBAMwZNzmJhgIA+sxvWVnhGGdvuXEUAOUAduAe8xJBE6en21407kejqJFguYwF1aPUx9ddtrjikjvA7dRUZscDdvYx6to7ffVYbhV33nWufE8sYw5FS4r5pPsWjtI1IX061HwxWiwb3HFyQY2qsebOKq5IH2V22eXscsk2kI1/EiY8MpFJe4NiT7WGtMcx/FuTsDEztrQob7gqXBW2NwP3UsbyshOoHtGBsIEEeBpv4hauu2YowQaL3SEA3ABOh8fias67wASwbArtuGIG35tMeFS6X7NEdEzBZnMEXWB7OuimB1y12rM+EiLh5jZisEuFwq9m57S6yq7gQcrBiwXqBHvNYcTwSpxNbNuQqXLKjx+9oSfXU1hfus96zbcH74NxvHh+/Wt9m6LnF7pJ0F9/+GCv1istNRbfkzcVuq80WlyvwvPcd+zQhSol+9Oup8jAI26mpZgeX7bJmdSrOAWAOxIBiTv8A+aauTcdbRbiu0P8AfD3SFy+yO9ESSdpmt13mVLlx7aMoRLWYHvZiwIJPQZQoI9fKufp4QlBSasrkclNpG/jPCrFi075ioCMdYjbSvPPKC/d0/MP1RVrcROJv2T2ndtMGyEjUkgiZ6jvbT5aVVfJ4jEqPBWmuxY3jxTtVa4MtNZI2eoQyZMxE5ZB0k6QD9VQz5UMdYFlDdt9plusoUsQJCTJCmSNfKtHLHOKPg/41etI5d1drl1EYg94PBjToPSq15l5kttiO2Vrd0s2YqVbKDHiAPoNEXJzS+tHPkS0S8/uNHA+L2rXFbeIYC3aF60TlByooyBoGp6E1cfFOYMO9whb9qWhlBuKDuYMEyNCNK888TxCvfZ1XKpKnLqQNBIneCZ+NOI4lamybZVWUNn7hjNnJUkNIbQ+Fay9P4jqwx5NEVsWrx2DbnKPbZcwgh4XWGG41B99VHoMThu8rD7mDAIj7oylWnQnzGmvrT9heN3rrsGvyhZ3K51ChnMnKgOg06CBFQ4ue0EnZx+tP7al0/TeEpQbstlyqbjJKiw8Rw1PbgKJ9oErB+nypUwsTDtsfaIcHw6AimriPFmyFZUEgGQIYZTPtzBnXYbVlw3mNDo/dCoNcrNmIPgoJ1AG8DeueEcsY6lv6q/udbyQvfYeMIh7ZSqjLmXqRGseh60w87WRbxXaHYovqTqpp0w3F7C95XbIpGrhgdWJ0JHifHamjn/Ei5ftZdVysB7z4/CqdO5T6j9Ua2rivoY6lKOK077+ZJOFWw6owIYEKTBiJjTvR41sxuBQESJBGxkj0n4V0cpIfmtuDoV+ru/8ATTpZxODDsMSSwSMwRZIJ1AmZAI1muPLlnDK8aT2fYrCEZY1NtcEb5k4Gn8GG/bGquyuZ/ksrL9BPwqLcr627iGCAwMFQw7wjr+bVlcXvYZsNfw+HFwI4LEN0JVhCSSTAA1JqtuV1YXGWDJWdPIx9td/RZNWKT+e/yOLqIVNDr81X+bt/o/40V2Zh4/R/hRV9bJeGixrdu5rNwkADom46GR+81oNtyXnKRsoa1/KHeJM6g6jptT7aw2GdSVvIACwBZmYQR+WfOPdWv5th+yLretLAciHUDckTBE7zXG5srSIoMG5UlrGEcgZjmssns/pfuazt8P1CDC4bWV+53Li+LE/exGxG9So8OtMoC3kJJUdy6pBgg7bDQGt9jgsOO9BIY6ZCRqOuWDvG1JzYbETxGBcEgYe6IAM28Y6gTIGhYbZTXAuOvoJUY0QW1GItNoAQ2jXCen7IqffwPc+6EMSdFgqhkAZvL+UevSmXE8ExLWp7rZoHsaw2gIBuAazEHxoWRhSIrd47iliL+NAOgzLZfYGYyhpOm/lTpyxxVzag3Mzi61wlkUW1trbCqCVABAZnMSDpuARScZ4JfWO4vdBYQpHTWBm1OvlTemGuLY2VfaDbxLEBco8YG/7KpDJHlmJJ9iLY7iL3MZhC5U5TOlnJuy5pG7be6DTHgL2bGu4PtNdcnbVyxP11McfwO52y4gmMluAo6e0Sxnf2th4VDEwqyfZAmJaROu+37avjSmqXkaSkqfx+pNcJxoG5aw/ae1fsrlJGvaMEgAKx00J1G8DXWrP5i4etjDgW1CKXVSF0EGdCu0HTSqL5cIGOwqgr+EYcggyD92XTTY6HeOnjXojnNf4sfz7f64rohi8JbdzDbyZE5dmcPHMIRgRt3FB+JWvNzWGVmh8plhIkHfaRXp3mJv4g8fza/RFeaO8GYhCYZjJPdgMR9da/XNulf/prJK61Ot2crWQIJMn/AMVY3L3JODv4dLjvdJckHKDCRABnKZkmP31glvBvcbTIJ1JkgCeh8/IftrK9YvjurBkgd0hgRGhPWNevn4Gq+Fkit4nLlwTk7iaeeeEJhMY1i2zMqpaYFt5ZAxB9CSKj8U8Yzhd4tOS42ntFHE/ETFcVzA3ACxRgAYJIgCZjf0NZVBTSpmlxWBNdRw/dkEHST4+6uQmnPYUXZmuLcaTW5OJtEESIjcx8DNcVKFqVJlb2O04tDIYNBOoBifDy6eFdePui4LRQHu6GfdT9y1aS5hlVrYYh2kl11BIOXI2nv86abNohsTaAWEdn2MwjEdzWNh16CnGCtNkI59TlFdhy4TxB7aKqXikMdJ016lTpGv0V1Yq6zsVLZyWgvrr01yvEAeHSmGyhaYHSdxt8ayuYdhrlPu8wNPpr0fCi/wBSr9jn8R1pb+pLMLilWyy5WzBgqsZ74AbOI8iV330864Pk7eb99CYYAlXHtKM5zBdepIPupk4SLhvIj3LiIxGpJyqSNzm0FOHKN0JjwRMPnGu+q59fOViuDH0ui35nf42ql5FifMR/O3Pgn/bRXR2g8R8RRW/Dj5G7GfGYjiFpQxuXFkSCDG66ajrM1qtY3GFT+MNdGWQRvt6b1JbfJ2JDAlbwRIOmKuMSdDKzcMRlA9QDWrEYO/ahm+eos6lrqQSSTAzNvOU7dCOteV4cl3LJ3whmW5ibiszWrThTBDWwSJ20J8BP+rXNcut1w1gRP+iUbeGlOD3LiHS/iVYiBmZSMwG8FDpMmOs71vFvFPAt4ts+hIcoVyjaP4vvM7nXSsb/ANS/c1ol/S/2GR3eT9yyg/zZ7PbKZ0BImKR7r7TiV1EAYm4AOo2A2j/xU7t8ExBYImKuG4Ua4odLJByFBqwtDSWAPUiPCnA8tYgkg3hOpH8XtGRCySwAAbMSI8PfWljm99v3JuSWzRVmL4pfUT2uL9DibjCCYIgnyBrh/hq8SAz3WHgXaNNfHTXWrA41wbFIo7U2RmClU7G0Wd+ttfugnXIB45tYiaZeGcGvXHy3bKkk9LZRROneYwPHQHpvVseJvlIjPLGJH7vGrtyUVsSxYFQue2wOYRsLeb6a04nk/G2lVrtnIpggPcVCfQFpB92lWHws4iziVwmECWjmDsboUsQQDlHdmAJOhk7yK6Od+EMjZjdZ2va3F9m33YywsnQdASYiuzEvDlUQqE4apyrZ0t3vxT8iueWeAuuLw7M1gRiLDQLmZ4W6pyjL3Z061evOR/irfn2/11quOXuHRdRtNLiHbwYVMOcscnYZRcXNnTu5pJAMnTeNPoqmSTyV8LHBKGmu7O3j9z/7cfO39lU2MLh1Zs2HXM2aJLXO8euVtBOu23SrJxXE7F3CFLYuu5tgDunLmMDeIidKZ7nCwpcAQDkMeBKhvtNcPWTcJKMHvv8ADuelheGLbyw1Lf4bkMs2iD3bdlPzbaz8TW+7bu9bje7QfQKkScNAbauq9w5Y2rmdvkxHIl2IX81vrBZ2GZQwOcv7u8PSs+0vL/pJ9VBqTYTAjYjbTbzJ+01uxnDFIqeN1sx583iz1VXpsRviuAu3RZDWUuZrdtx2dsK3eOud9NIPnMU3YDl7D3bYZrYk+7fWNPWpHwxzh2ci3nLCAM2XaesHxrjwNs20CNoQPGR8YE/CuzG9zmlVEYs8MwRN222GfMCQrW7zSsNElXBB2pbPLuEmZxA8myMP7IBqT3sKWg/vqZpwwnBMwqMcku7NZVCUrgqRpv8AH1uWhhXuWVtm3lJGEYXNNu+pAUHY6edQD+BriXWyuHBzLmBPeDaTr61Pr/AULd9AxiAT0/bTXxTB9ncI9D8RVMWWVtMJ48LjHTd1v/0M2A5bxOVggQuxyKDctq+UiWIVm16DTxrevJHEYM2nECRENJ8BkJ19dKktnhC4pgCT3O8Ijy30PlUs5VwWGXsy7+05RfuVwKzywyi4VyzKt16Gup9XNTcF2ON9Pj0qSe75+BTeLweMw7q1yxetlWQ623CykQTpEwK48ChTE2QTqbiAkbwXgnTy1r0lzpwO9ibATD3TZKmYEgONgCV1HjUGscAVbYRwrNpLZevjFXxZ3KVSRp9PCOPWpb+Xcf8A/IgeFz4r+yinP+Gm8aK3WT4E9K8zo4FwXD2LTjDhYZRJc+00EjN0y69POuXmHhdvE4YNaSWUMFa2ABJEMcsgOJA921Vny5zBcs4q01whu0gZRrlc5QI1iQTB/wBfwNT/AInzObWAfFYe5bvdnchuxh1AYy2YzJIk6gjUjYCuXwk/08lPFnCWqMqa7jc44kmQW0S6oAXJ82QEBehPbqQY8M225p2xGDOMsojYe5YOjOGtgCVIIhmDLljNpOkjeK18K5suXL+HtwWS9YF0mFDWyqKxzaxqS0iWIzLoN6rfnfmgYlrJztabIZt5JTvkuCrCJBQ2+kd3cyaJwUlTRjFKUJKWrfm/5ZbPCLwssqM65LVguCiyArMoWXUQT7RgabGm3hOPXGXMXbXEu1oEtKsxCIRbKDvad4i8fTTQCo3Z+UqzbayELFbNq3bZTKkgAB4nQt3diQNBrUZbnpXv4m0EFmxiGeXXPmdGzKGuAEkd12MAdAOlYUdmqOinrjLVvzfq+5aHBMXLqLd7tLRgWyNRCt3iG1yyOk/VTrguX7YbPeQXHLFgzMSN+6cu2aANYmq2+TziWA4egS7jUYTduMwzBCzhFRVUd/REZj3RBPpUy4Rz9YulUAzBiTbOZAxUmVhCZMAgEj9tEVNOktiGVRbuT3HaxwW2uMOI7QKRoLYAAkpB16mJNHMPDRiDrmDKrZYiDEkTpOugpn5r+UPBcOcJdtu95wHa3byMU3Cl2LAA6bDX3UnD/lFwuKw9zEYdWNy0ADacqjS20sTGWATInY9asnL2n5EpYm6hDlv7/Y5+JYNbIQWyHzFlfMQQMqydAPNRHnTfwy329vEtlS2LEDuiS5YAwRoFA8vGovwO+tqxevNaa2rPeutcL51Yns5Cgd4wSdSBMga1q5d5juD5zbtg9leuKWYgAQLNsRBXNmkgxoAKMkoeHst2WwYMscjxye8dn8uTqwfFnRlS/cy4ctlJCLmREgyhA1PTUGn2zxThoC3Pn1woxBTU96FlhlFqJzQsNUc5tRVwFm5HeJYEgwSBmyjw3jWKr6xdKKCj3DbIGdAQB3WISd+skSNI0mvPiubOyez2OzEc34p2a4brqpaQBA3OoGWAsaxp0qffIrduY+7iFxNx3tottsmYgEsXGsa6RVe8IuZglguqq93rbDlTC96WkZdhBG+uvSxvk14jY4ccZfuuiWR2CZhJOxibYBI1bcaEztWpVF8C0ycNXYsrC8pYcNcMNOYZSGIKQixEdfWoJjOD3rV26bl93XO3ZpIBiTqxB228z+T1sCzxxJeCCCQQRJ/FFVvxDmRbmOvWJGRFkkSXzaEjwy6qPU15nXyn4f8AhL19Dp6GKeT9ZzWrLlwc0gTmB8IMR74rTjh3h76bbHHBcvKUMKTGXUaEGDr7Uxvtv50vGeMW0zkHMbYJYCdyAQJiJ2rq/wDn61Cp8/EOv067jVV2JXyxwi5fLCRlyHLI9kxoQRufWRrtTjwjhl8uUzaA96VEj4VAuT/lOvqlzDrZtrcNu41q7IyqVXN90ViJEKdQfcaf7XO2LvWTcw1pEvdsLZKQ+a2bLXM8OO6MygTtMaiufwsqyvXJ02/Ljt+SdqUbSXYlvG+Em2odHVlHtMQZ1bKNJHXT1qI8yYC5nBKknIJhTpDMuoPpWrmjn+6LfZK8tcRDmNnKuuR2ALDo2YHzU7EU38a55sYpluF1RPYQkEOWIVnzjWIYtrtDCuzQ4zWm2t/l5EVst6scuX8c1psyrn7pEE5eo6wfCp5wvgkhScTKhy6oEWQTdF0HcknYT4dKorjHMd624XDjIxzfihzBkaDUbeXSpp8nvO2LfEW7OKUqDqCMttWyidQ4gbEkgjaI106XC8mrbhEnShunsXjNQnF2O+VOneg9Y18t6cb3H3RTfu2mW1aRnuEDMYVWYxlYqywDrO8VCuOfKFY7A4hLN3O7EJZuLkaVIDEuAy5dZkEztvMWg9zHKHn5u/8AJPwNFQj/AOrl3/8AXW//AFDf9tFdPiMh4cjjwu7/ANDc/wCWNaeCfgWL/Mwn61yiipYvfL1/KOjL7p+i/Jv4P98wfriv+Zv1AcZ9/f8ANt/3Iooq2T2vmc64+Ru4j97f+k+xa1v96X/af+1RRXKvbfqdS9n5IOC/fF/1v1Wqf8k/gln+sD9ZaKKvDk5svsFf8e/CsV/T3f1zXRy9/pP9n+saKKc/dv0Ojov8xj/uX3RNOKf5qt/0tz9a3TVyh+N6t/drRRXFP2V6I7/91m/vyfkdueP824f+nH1mq74n/ofR/wC8eiipw7ev4IZOWOfLv31Pz7f1mnn/APHcU/pcP/fJRRTy+2v53HD3D9fwiwuWPwaz/RWv7taq3hP+cL3riP16KK4pe7n6M3g97H1Q58D2t/1e1+s1NWI+84v+kf8AUSlorpxe2wz8L0/I3cq/j/mXP1Grdw/e5/V7f69qiitf6pGoe7Xp/wAk/wDle9qz+ZiPqs1TdyiirYeDhnySrhv4bZ9W+tqnmA/DcP8Ann9R6KKivfxOzJ/ksvz+xYGJ/B8d/Vz9T1SHMv3wep+pKKK6n7bObF7r5jXRRRTGf//Z",
                    card2 = "https://encrypted-tbn0.gstatic.com/images?q=tbn%3AANd9GcRdXp-MREaa6k7N1cD1UjvDLnvmb_iGS6qioQ&usqp=CAU",
                    card3 = "https://encrypted-tbn0.gstatic.com/images?q=tbn%3AANd9GcRdXp-MREaa6k7N1cD1UjvDLnvmb_iGS6qioQ&usqp=CAU",
                    card4 = "https://encrypted-tbn0.gstatic.com/images?q=tbn%3AANd9GcRdXp-MREaa6k7N1cD1UjvDLnvmb_iGS6qioQ&usqp=CAU",
                    name_of_project = "평화의 브레인스토밍"
                )
            )
            add(
                ParticipatedProjectsData(
                    card1 = "https://encrypted-tbn0.gstatic.com/images?q=tbn%3AANd9GcRdXp-MREaa6k7N1cD1UjvDLnvmb_iGS6qioQ&usqp=CAU",
                    card2 = "https://encrypted-tbn0.gstatic.com/images?q=tbn%3AANd9GcRdXp-MREaa6k7N1cD1UjvDLnvmb_iGS6qioQ&usqp=CAU",
                    card3 = "https://encrypted-tbn0.gstatic.com/images?q=tbn%3AANd9GcRdXp-MREaa6k7N1cD1UjvDLnvmb_iGS6qioQ&usqp=CAU",
                    card4 = "https://encrypted-tbn0.gstatic.com/images?q=tbn%3AANd9GcRdXp-MREaa6k7N1cD1UjvDLnvmb_iGS6qioQ&usqp=CAU",
                    name_of_project = "평화의 브레인스토밍"
                )
            )
            add(
                ParticipatedProjectsData(
                    card1 = "https://encrypted-tbn0.gstatic.com/images?q=tbn%3AANd9GcRdXp-MREaa6k7N1cD1UjvDLnvmb_iGS6qioQ&usqp=CAU",
                    card2 = "https://encrypted-tbn0.gstatic.com/images?q=tbn%3AANd9GcRdXp-MREaa6k7N1cD1UjvDLnvmb_iGS6qioQ&usqp=CAU",
                    card3 = "https://encrypted-tbn0.gstatic.com/images?q=tbn%3AANd9GcRdXp-MREaa6k7N1cD1UjvDLnvmb_iGS6qioQ&usqp=CAU",
                    card4 = "https://encrypted-tbn0.gstatic.com/images?q=tbn%3AANd9GcRdXp-MREaa6k7N1cD1UjvDLnvmb_iGS6qioQ&usqp=CAU",
                    name_of_project = "평화의 브레인스토밍"
                )
            )
            add(
                ParticipatedProjectsData(
                    card1 = "https://encrypted-tbn0.gstatic.com/images?q=tbn%3AANd9GcRdXp-MREaa6k7N1cD1UjvDLnvmb_iGS6qioQ&usqp=CAU",
                    card2 = "https://encrypted-tbn0.gstatic.com/images?q=tbn%3AANd9GcRdXp-MREaa6k7N1cD1UjvDLnvmb_iGS6qioQ&usqp=CAU",
                    card3 = "https://encrypted-tbn0.gstatic.com/images?q=tbn%3AANd9GcRdXp-MREaa6k7N1cD1UjvDLnvmb_iGS6qioQ&usqp=CAU",
                    card4 = "https://encrypted-tbn0.gstatic.com/images?q=tbn%3AANd9GcRdXp-MREaa6k7N1cD1UjvDLnvmb_iGS6qioQ&usqp=CAU",
                    name_of_project = "성규의 브레인스토밍"
                )
            )
            add(
                ParticipatedProjectsData(
                    card1 = "https://encrypted-tbn0.gstatic.com/images?q=tbn%3AANd9GcRdXp-MREaa6k7N1cD1UjvDLnvmb_iGS6qioQ&usqp=CAU",
                    card2 = "https://encrypted-tbn0.gstatic.com/images?q=tbn%3AANd9GcRdXp-MREaa6k7N1cD1UjvDLnvmb_iGS6qioQ&usqp=CAU",
                    card3 = "https://encrypted-tbn0.gstatic.com/images?q=tbn%3AANd9GcRdXp-MREaa6k7N1cD1UjvDLnvmb_iGS6qioQ&usqp=CAU",
                    card4 = "https://encrypted-tbn0.gstatic.com/images?q=tbn%3AANd9GcRdXp-MREaa6k7N1cD1UjvDLnvmb_iGS6qioQ&usqp=CAU",
                    name_of_project = "성규의 브레인스토밍"
                )
            )
            add(
                ParticipatedProjectsData(
                    card1 = "https://encrypted-tbn0.gstatic.com/images?q=tbn%3AANd9GcRdXp-MREaa6k7N1cD1UjvDLnvmb_iGS6qioQ&usqp=CAU",
                    card2 = "https://encrypted-tbn0.gstatic.com/images?q=tbn%3AANd9GcRdXp-MREaa6k7N1cD1UjvDLnvmb_iGS6qioQ&usqp=CAU",
                    card3 = "https://encrypted-tbn0.gstatic.com/images?q=tbn%3AANd9GcRdXp-MREaa6k7N1cD1UjvDLnvmb_iGS6qioQ&usqp=CAU",
                    card4 = "https://encrypted-tbn0.gstatic.com/images?q=tbn%3AANd9GcRdXp-MREaa6k7N1cD1UjvDLnvmb_iGS6qioQ&usqp=CAU",
                    name_of_project = "성규의 브레인스토밍"
                )
            )
        }

        participatedProjectsAdapter.datas = datas
        participatedProjectsAdapter.notifyDataSetChanged()

    }
}